package org.littleshoot.proxy;

import org.junit.Test;

import io.netty.handler.codec.http.DefaultHttpRequest;
import io.netty.handler.codec.http.HttpMethod;
import io.netty.handler.codec.http.HttpRequest;
import io.netty.handler.codec.http.HttpVersion;

import java.net.URISyntaxException;


import static org.littleshoot.proxy.RegexHttpRequestFilter.*;
import static org.mockito.Mockito.*;


public class RegexHttpRequestFilterTest {

    private final HttpRequestFilter delegate = mock(HttpRequestFilter.class);
    private RegexHttpRequestFilter filter;

    @Test public void testHostWhenMatchDomain() throws Exception {
        filter = newHostFilter(".*google.com", delegate);

        match("http://google.com/testing");
    }

    @Test public void testHostWhenMatchSubdomain() throws Exception {
        filter = newHostFilter(".*google.com", delegate);

        match("http://www.google.com/testing");
    }

    @Test public void testHostWhenDonNotMatch() throws Exception {
        filter = newHostFilter(".*google.com", delegate);

        doNotMatch("http://example.com/testing");
    }

    @Test public void testPathWhenExactMatch() throws Exception {
        filter = newPathFilter("/testing", delegate);

        match("http://google.com/testing");
    }


    @Test public void testPathWhenMatchPattern() throws Exception {
        filter = newPathFilter("/test.*", delegate);

        match("http://google.com/testing");
    }

    @Test public void testPathWhenDoNotMatch() throws Exception {
        filter = newPathFilter("/testing", delegate);

        doNotMatch("http://google.com/test");
    }

    @Test public void testHostAndPathWhenMatch() throws Exception {
        filter = newHostAndPathFilter(".*.google.com", "/testing", delegate);

        match("http://www.google.com/testing");
    }


    @Test public void testHostAndPathWhenDoNotMatch() throws Exception {
        filter = newHostAndPathFilter(".*.google.com", "/testing", delegate);

        doNotMatch("http://www.google.com/test");
    }


    private void match(String uri) throws URISyntaxException {
        final HttpRequest request = createRequest(uri);
        filter.filter(request);

        verify(delegate).filter(request);
    }

    private void doNotMatch(String uri) throws URISyntaxException {
        final HttpRequest request = createRequest(uri);
        filter.filter(request);

        verify(delegate, never()).filter(request);
    }

    private HttpRequest createRequest(String url) {
        final HttpRequest request = new DefaultHttpRequest(HttpVersion.HTTP_1_0, HttpMethod.GET, ProxyUtils.stripHost(url));
        request.setHeader("Host", ProxyUtils.parseHost(url));
        return request;
    }

}
