﻿using Microsoft.Extensions.FileProviders;
using Microsoft.Extensions.Logging;
using System;
using System.Collections.Generic;
using System.IO;
using System.Linq;
using System.Text.Json;
using System.Threading.Tasks;

namespace Gov.CitizenApi.Features.Lookups
{
    public interface ILookupsService
    {
        IEnumerable<Statute> GetStatutes();
        Statute GetCountStatute(string code);
        Task<LookupsAll> GetAllLookUps();
    }

    public class LookupsService : ILookupsService
    {
        private ILogger<LookupsService> _logger;
        private static LookupsAll _lookups;
        public LookupsService(ILogger<LookupsService> logger)
        {
            _logger = logger ?? throw new ArgumentNullException(nameof(logger));
            if (_lookups == null)
            {
                var provider = new ManifestEmbeddedFileProvider(GetType().Assembly);
                var fileInfo = provider.GetFileInfo("Features/Lookups/TempData/LookupData.json");
                if (!fileInfo.Exists)
                {
                    throw new Exception("Cannot find lookup file");
                }

                Stream fileStream = fileInfo.CreateReadStream();
                using StreamReader reader = new StreamReader(fileStream);
                _lookups = JsonSerializer.Deserialize<LookupsAll>(reader.ReadToEnd());
            }
        }

        public LookupsService()
        {
            
        }

        public IEnumerable<Statute> GetStatutes()
        {
            if (_lookups == null) return null;
            return _lookups.statutes;
        }

        public Statute GetCountStatute(string countCode)
        {
            if (_lookups == null) return null;
            return _lookups.statutes.FirstOrDefault(m=>m.code==Int32.Parse(countCode));
        }

        public Task<LookupsAll>  GetAllLookUps()
        {
            return Task.FromResult<LookupsAll>(_lookups);
        }
    }
}
