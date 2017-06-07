package com.wordpress.abhirockzz.jaxrs.sse;

import java.util.Date;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.sse.OutboundSseEvent;
import javax.ws.rs.sse.Sse;
import javax.ws.rs.sse.SseEventSink;

@Stateless
@Path("events")
public class EventsResource {

    @Inject
    Broadcaster broadcaster;

    //one time
    @Path("fetch")
    @GET
    @Produces("text/event-stream")
    public void fetch(@Context Sse sse, @Context SseEventSink eSink) {
        OutboundSseEvent event = sse.newEvent("one-time-event", new Date().toString());
        eSink.send(event);
        System.out.println("event sent");
        eSink.close();
        System.out.println("sink closed");
    }

    //registeration
    @Path("subscribe")
    @GET
    @Produces("text/event-stream")
    public void subscribe(@Context SseEventSink eSink) {
        broadcaster.register(eSink);
    }

}
