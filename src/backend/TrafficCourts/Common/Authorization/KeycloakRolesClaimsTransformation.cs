﻿
using Microsoft.AspNetCore.Authentication;
using System.Security.Claims;
using System.Text.Json;

namespace TrafficCourts.Common.Authorization
{
    /// <summary>
    /// Transforms keycloak roles in the resource_access claim to jwt role claims.
    /// </summary>
    /// <example>
    /// Example of keycloack resource_access claim
    /// "resource_access": {
    ///     "blog-api": {
    ///         "roles": [
    ///             "editor"
    ///         ]
    ///     },
    ///     "account": {
    ///         "roles": [
    ///             "view-profile"
    ///         ]
    ///     }
    /// },
    /// </example>
    /// <seealso cref="IClaimsTransformation" />
    public class KeycloakRolesClaimsTransformation : IClaimsTransformation
    {
        private readonly string _roleClaimType;
        private readonly string _audience;

        /// <summary>
        /// Initializes a new instance of the <see cref="KeycloakRolesClaimsTransformation"/> class.
        /// </summary>
        /// <param name="roleClaimType">Type of the role claim.</param>
        /// <param name="audience">The audience.</param>
        public KeycloakRolesClaimsTransformation(string roleClaimType, string audience)
        {
            if (string.IsNullOrEmpty(roleClaimType))
            {
                throw new ArgumentException($"'{nameof(roleClaimType)}' cannot be null or empty.", nameof(roleClaimType));
            }

            if (string.IsNullOrEmpty(audience))
            {
                throw new ArgumentException($"'{nameof(audience)}' cannot be null or empty.", nameof(audience));
            }

            _roleClaimType = roleClaimType;
            _audience = audience;
        }

        /// <summary>
        /// Provides a central transformation point to change the specified principal.
        /// Note: this will be run on each AuthenticateAsync call, so its safer to
        /// return a new ClaimsPrincipal if your transformation is not idempotent.
        /// </summary>
        /// <param name="principal">The <see cref="T:System.Security.Claims.ClaimsPrincipal" /> to transform.</param>
        /// <returns>
        /// The transformed principal.
        /// </returns>
        public Task<ClaimsPrincipal> TransformAsync(ClaimsPrincipal principal)
        {
            var result = principal.Clone();
            if (result.Identity is not ClaimsIdentity identity)
            {
                return Task.FromResult(result);
            }

            var resourceAccessValue = principal.FindFirst("resource_access")?.Value;
            if (string.IsNullOrWhiteSpace(resourceAccessValue))
            {
                return Task.FromResult(result);
            }

            using var resourceAccess = JsonDocument.Parse(resourceAccessValue);
            var clientRoles = resourceAccess
                .RootElement
                .GetProperty(_audience)
                .GetProperty("roles");

            foreach (var role in clientRoles.EnumerateArray())
            {
                var value = role.GetString();
                if (!string.IsNullOrWhiteSpace(value))
                {
                    identity.AddClaim(new Claim(_roleClaimType, value));
                }
            }

            return Task.FromResult(result);
        }
    }
}