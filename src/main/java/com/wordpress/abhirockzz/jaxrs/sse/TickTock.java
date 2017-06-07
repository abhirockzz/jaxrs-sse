package com.wordpress.abhirockzz.jaxrs.sse;

import java.util.Date;
import javax.annotation.Resource;
import javax.ejb.Singleton;
import javax.ejb.Timeout;
import javax.ejb.Timer;
import javax.ejb.TimerService;
import javax.inject.Inject;
import javax.ws.rs.Path;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.sse.OutboundSseEvent;
import javax.ws.rs.sse.Sse;

@Singleton
//@Startup
@Path("")
public class TickTock {

    @Context
    Sse sse;

    @Resource
    TimerService tsvc;

    Timer timer;

    public void init() {
        this.timer = this.timer == null ? tsvc.createTimer(10000, 5000, null) : this.timer;
        System.out.println("timer created......");
    }

    @Inject
    Broadcaster broadcaster;

    @Timeout
    public void onTrigger() {
        System.out.println("timer triggered");
        for (int i = 0; i < 3; i++) {
            OutboundSseEvent event = sse.newEventBuilder().name("key-" + i).data("value-" + i + " at " + new Date().toString()).mediaType(MediaType.TEXT_PLAIN_TYPE).build();
            broadcaster.broadcast(event);
        }
        System.out.println("broadcasted events via timer.........");

    }
}
