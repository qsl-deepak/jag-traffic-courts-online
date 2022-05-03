﻿using MassTransit;
using TrafficCourts.Messaging.MessageContracts;
using TrafficCourts.Workflow.Service.Models;
using TrafficCourts.Workflow.Service.Services;

namespace TrafficCourts.Workflow.Service.Features.Mail
{
    /// <summary>
    ///     Consumer for SendEmail message.
    /// </summary>
    public class SendEmailConsumer : IConsumer<SendEmail>
    {
        private readonly ILogger<SendEmailConsumer> _logger;
        private readonly IEmailSenderService _emailSenderService;

        public SendEmailConsumer(ILogger<SendEmailConsumer> logger, IEmailSenderService emailSenderService)
        {
            _logger = logger;
            _emailSenderService = emailSenderService;
        }

        public async Task Consume(ConsumeContext<SendEmail> context)
        {
            if (context.RequestId != null)
            {
                _logger.LogDebug("Consuming message: {MessageId}", context.MessageId);

                _logger.LogDebug("TRY SENDING EMAIL: {Email}", context.ToString());

                await _emailSenderService.SendEmailAsync(context.Message, context.CancellationToken);
            }
        }
    }
}