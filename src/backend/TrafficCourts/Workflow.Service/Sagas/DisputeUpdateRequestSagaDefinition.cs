﻿using MassTransit;

namespace TrafficCourts.Workflow.Service.Sagas;

public class DisputeUpdateRequestSagaDefinition : SagaDefinition<DisputeUpdateRequestSagaState>
{
    protected override void ConfigureSaga(IReceiveEndpointConfigurator endpointConfigurator, ISagaConfigurator<DisputeUpdateRequestSagaState> sagaConfigurator)
    {
        // configure the saga to retry the message when we get a ConcurrencyException
        // https://stackoverflow.com/questions/71350562/how-to-configure-retry-for-masstransit-sagas
        sagaConfigurator.UseMessageRetry(r =>
        {
            r.Handle<ConcurrencyException>();
            r.Interval(5, TimeSpan.FromMilliseconds(100));
        });

        base.ConfigureSaga(endpointConfigurator, sagaConfigurator);
    }
}
