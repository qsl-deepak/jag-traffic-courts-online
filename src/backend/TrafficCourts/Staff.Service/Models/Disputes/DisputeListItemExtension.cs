﻿using TrafficCourts.Domain.Models;

namespace TrafficCourts.Staff.Service.Models.Disputes;

public static class DisputeListItemExtension
{
    public static IQueryable<DisputeListItem> Filter(this IEnumerable<DisputeListItem> items, GetAllDisputesParameters? parameters, IList<Agency>? agencies = null)
    {
        IQueryable<DisputeListItem> query = items.AsQueryable();
        if (parameters is null)
        {
            return query;
        }

        if (parameters.ExcludeStatus is not null && parameters.ExcludeStatus.Count != 0)
        {
            string[] values = parameters.ExcludeStatus.Select(_ => _.ToString()).ToArray();
            query = query.Where(_ => !values.Contains(_.Status.ToString()));
        }

        if (parameters.Status is not null && parameters.Status.Count != 0)
        {
            string[] values = parameters.Status.Select(_ => _.ToString()).ToArray();
            query = query.Where(_ => values.Contains(_.Status.ToString()));
        }

        if (parameters.From is not null)
        {
            query = query.Where(_ => parameters.From.Value <= _.SubmittedTs);
        }

        if (parameters.Thru is not null)
        {
            query = query.Where(_ => _.SubmittedTs <= parameters.Thru.Value);
        }

        if (!string.IsNullOrEmpty(parameters?.TicketNumber))
        {
            string value = parameters.TicketNumber.Trim();
            query = query.Where(_ => _.TicketNumber.Contains(value, StringComparison.OrdinalIgnoreCase));
        }

        if (!string.IsNullOrEmpty(parameters?.Surname))
        {
            string value = parameters.Surname.Trim();
            query = query.Where(_ => _.DisputantSurname.Contains(value, StringComparison.OrdinalIgnoreCase));
        }

        if (agencies is not null && !string.IsNullOrEmpty(parameters?.CourtHouse))
        {
            string value = parameters.CourtHouse.Trim();

            // search for agency 
            HashSet<string> agencyIds = agencies
                .Where(agency => agency.Name.Contains(value, StringComparison.OrdinalIgnoreCase))
                .Select(agency => agency.Id)
                .ToHashSet();

            query = query.Where(_ => agencyIds.Contains(_.CourtAgenId));
        }

        return query;
    }
}
