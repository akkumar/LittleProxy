package org.littleshoot.proxy;

import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.timeout.IdleState;
import io.netty.handler.timeout.IdleStateEvent;
import io.netty.handler.timeout.IdleStateHandler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * This handles idle sockets.
 */
public class IdleAwareHandler extends IdleStateHandler {

    private static final Logger log = 
        LoggerFactory.getLogger(IdleAwareHandler.class);
    private final String handlerName;
    
    public IdleAwareHandler(final String handlerName) {
        this.handlerName = handlerName;
    }

    @Override
    public void channelIdle(final ChannelHandlerContext ctx, 
        final IdleStateEvent e) {
        if (e.state() == IdleState.READER_IDLE) {
            log.info("Got reader idle -- closing -- "+this);
            ctx.channel().close();
        } else if (e.state() == IdleState.WRITER_IDLE) {
            log.info("Got writer idle -- closing connection -- "+this);
            ctx.channel().close();
        }
    }
    
    @Override
    public String toString() {
        return "IdleAwareHandler [handlerName=" + handlerName + "]";
    }
}
