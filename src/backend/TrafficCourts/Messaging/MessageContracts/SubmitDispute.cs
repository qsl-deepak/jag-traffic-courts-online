﻿using System.Text.Json.Serialization;
using TrafficCourts.Common.Converters;

namespace TrafficCourts.Messaging.MessageContracts
{
    public interface SubmitDispute : IMessage
    {
        string TicketNumber { get; set; }
        string CourtLocation { get; set; }
        DateTime ViolationDate { get; set; }
        string DisputantSurname { get; set; }
        string GivenNames { get; set; }
        string StreetAddress { get; set; }
        string Province { get; set; }
        string PostalCode { get; set; }
        string HomePhone { get; set; }
        string EmailAddress { get; set; }
        string DriversLicence { get; set; }
        string DriversLicenceProvince { get; set; }
        string WorkPhone { get; set; }
        [JsonConverter(typeof(DateOnlyJsonConverter))]
        DateOnly DateOfBirth { get; set; }
        string EnforcementOrganization { get; set; }
        [JsonConverter(typeof(DateOnlyJsonConverter))]
        DateOnly ServiceDate { get; set; }
        IList<TicketCount> TicketCounts { get; set; }
        bool LawyerRepresentation { get; set; }
        string InterpreterLanguage { get; set; }
        bool WitnessIntent { get; set; }
        string OcrViolationTicket { get; set; }
    }
}