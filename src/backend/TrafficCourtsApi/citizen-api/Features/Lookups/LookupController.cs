﻿using Microsoft.AspNetCore.Http;
using Microsoft.AspNetCore.Mvc;
using Microsoft.Extensions.FileProviders;
using Microsoft.Extensions.Logging;
using NSwag.Annotations;
using System;
using System.Collections.Generic;
using System.IO;
using System.Text.Json;
using System.Threading.Tasks;

namespace Gov.CitizenApi.Features.Lookups
{
    [Route("api/[controller]")]
    [ApiController]
    [OpenApiTag("Lookup API")]
    public class LookupController : Controller
    {
        private ILogger<LookupController> _logger;
        private ILookupsService _lookupsService;
        public LookupController(ILogger<LookupController> logger, ILookupsService lookupsService)
        {
            _logger = logger ?? throw new ArgumentNullException(nameof(logger));
            _lookupsService = lookupsService ?? throw new ArgumentNullException(nameof(lookupsService));
        }


        [HttpGet]
        [Produces("application/json")]
        [ProducesResponseType(typeof(ApiResultResponse<LookupsAll>), StatusCodes.Status200OK)]
        [ProducesResponseType(typeof(ApiMessageResponse), StatusCodes.Status500InternalServerError)]
        public async Task<IActionResult> Get()
        {
            _logger.LogDebug("Get all Look Up Tables now.");
            return Ok(new { result = await _lookupsService.GetAllLookUps() }); ;
        }
    }
}
