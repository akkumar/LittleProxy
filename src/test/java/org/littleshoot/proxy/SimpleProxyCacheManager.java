package org.littleshoot.proxy;

import io.netty.channel.Channel;
import io.netty.handler.codec.http.HttpRequest;
import io.netty.handler.codec.http.HttpResponse;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Future;


class SimpleProxyCacheManager implements ProxyCacheManager {

    public static final List<String> requests = new ArrayList<String>();
    
    public boolean returnCacheHit(HttpRequest request, Channel channel) {
        
        requests.add( request.getUri() );
        
        return false;
    }

    public Future<String> cache(HttpRequest originalRequest,
            HttpResponse httpResponse,
            Object response, ChannelBuffer encoded) {
        
        return null;
    }
    
}