﻿namespace TrafficCourts.Citizen.Service;

/// <summary>
/// Provides reasons why certain types or members can be excluded from code coverage.
/// </summary>
internal static class Justifications
{
    /// <summary>
    /// Poco do not implement any custom logic
    /// </summary>
    public const string Poco = "Plain old CLR object and only contains simple properties";

    /// <summary>
    /// Generated code or standard framework type of code.
    /// </summary>
    public const string Framework = "framework or generated";
}
