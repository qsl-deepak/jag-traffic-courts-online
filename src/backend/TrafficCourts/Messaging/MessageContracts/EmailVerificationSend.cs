﻿namespace TrafficCourts.Messaging.MessageContracts;

/// <summary>
/// Interface message contract for sending verifying emails. An email would go out to an end user with a verification link so they can confirm the address is correct.
/// </summary>
public class EmailVerificationSend
{
    public EmailVerificationSend(Guid noticeOfDisputeId)
    {
        NoticeOfDisputeId = noticeOfDisputeId;
    }

    public Guid NoticeOfDisputeId { get; set; }
}
