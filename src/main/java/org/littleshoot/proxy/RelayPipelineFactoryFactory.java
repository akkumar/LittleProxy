package org.littleshoot.proxy;

import io.netty.channel.Channel;
import io.netty.handler.codec.http.HttpRequest;


public interface RelayPipelineFactoryFactory {

    ChannelPipelineFactory getRelayPipelineFactory(HttpRequest httpRequest, 
        Channel browserToProxyChannel, RelayListener relayListener);

}
