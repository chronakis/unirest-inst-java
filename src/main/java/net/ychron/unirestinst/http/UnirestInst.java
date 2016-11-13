/*
The MIT License

Copyright (c) 2013 Mashape (http://mashape.com)

Permission is hereby granted, free of charge, to any person obtaining
a copy of this software and associated documentation files (the
"Software"), to deal in the Software without restriction, including
without limitation the rights to use, copy, modify, merge, publish,
distribute, sublicense, and/or sell copies of the Software, and to
permit persons to whom the Software is furnished to do so, subject to
the following conditions:

The above copyright notice and this permission notice shall be
included in all copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND,
EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF
MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND
NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE
LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION
OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION
WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */

package net.ychron.unirestinst.http;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.apache.http.HttpHost;
import org.apache.http.client.HttpClient;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.nio.client.CloseableHttpAsyncClient;

import com.mashape.unirest.http.HttpMethod;
import com.mashape.unirest.http.ObjectMapper;
import com.mashape.unirest.http.async.utils.AsyncIdleConnectionMonitorThread;
import com.mashape.unirest.http.options.Option;
import com.mashape.unirest.http.utils.SyncIdleConnectionMonitorThread;

import net.ychron.unirestinst.http.options.Options;
import net.ychron.unirestinst.request.GetRequest;
import net.ychron.unirestinst.request.HttpRequestWithBody;

public class UnirestInst {
	private Options options;
	private HttpClientHelper httpClientHelper;
	
	public UnirestInst() {
		super();
		options = new Options();
		httpClientHelper = new HttpClientHelper(options);
	}

	/**
	 * Set the HttpClient implementation to use for every synchronous request
	 */
	public void setHttpClient(HttpClient httpClient) {
		options.setOption(Option.HTTPCLIENT, httpClient);
		options.customClientSet();
	}

	/**
	 * Set the asynchronous AbstractHttpAsyncClient implementation to use for every asynchronous request
	 */
	public void setAsyncHttpClient(CloseableHttpAsyncClient asyncHttpClient) {
		options.setOption(Option.ASYNCHTTPCLIENT, asyncHttpClient);
		options.customClientSet();
	}

	/**
	 * Set a proxy
	 */
	public void setProxy(HttpHost proxy) {
		options.setOption(Option.PROXY, proxy);

		// Reload the client implementations
		options.refresh();
	}

	/**
	 * Set the ObjectMapper implementation to use for Response to Object binding
	 * 
	 * @param objectMapper Custom implementation of ObjectMapper interface
	 */
	public void setObjectMapper(ObjectMapper objectMapper) {
		options.setOption(Option.OBJECT_MAPPER, objectMapper);

		// Reload the client implementations
		options.refresh();
	}

	/**
	 * Set the connection timeout and socket timeout
	 * 
	 * @param connectionTimeout The timeout until a connection with the server is established (in milliseconds). Default is 10000. Set to zero to disable the timeout.
	 * @param socketTimeout The timeout to receive data (in milliseconds). Default is 60000. Set to zero to disable the timeout.
	 */
	public void setTimeouts(long connectionTimeout, long socketTimeout) {
		options.setOption(Option.CONNECTION_TIMEOUT, connectionTimeout);
		options.setOption(Option.SOCKET_TIMEOUT, socketTimeout);

		// Reload the client implementations
		options.refresh();
	}

	/**
	 * Set the concurrency levels
	 * 
	 * @param maxTotal Defines the overall connection limit for a connection pool. Default is 200.
	 * @param maxPerRoute Defines a connection limit per one HTTP route (this can be considered a per target host limit). Default is 20.
	 */
	public void setConcurrency(int maxTotal, int maxPerRoute) {
		options.setOption(Option.MAX_TOTAL, maxTotal);
		options.setOption(Option.MAX_PER_ROUTE, maxPerRoute);

		// Reload the client implementations
		options.refresh();
	}

	/**
	 * Clear default headers
	 */
	public void clearDefaultHeaders() {
		options.setOption(Option.DEFAULT_HEADERS, null);
	}

	/**
	 * Set default header
	 */
	@SuppressWarnings("unchecked")
	public void setDefaultHeader(String name, String value) {
		Object headers = options.getOption(Option.DEFAULT_HEADERS);
		if (headers == null) {
			headers = new HashMap<String, String>();
		}
		((Map<String, String>) headers).put(name, value);
		options.setOption(Option.DEFAULT_HEADERS, headers);
	}

	/**
	 * Close the asynchronous client and its event loop. Use this method to close all the threads and allow an application to exit.
	 */
	public void shutdown() throws IOException {
		// Closing the Sync HTTP client
		CloseableHttpClient syncClient = (CloseableHttpClient) options.getOption(Option.HTTPCLIENT);
		if (syncClient != null) {
			syncClient.close();
		}

		SyncIdleConnectionMonitorThread syncIdleConnectionMonitorThread = (SyncIdleConnectionMonitorThread) options.getOption(Option.SYNC_MONITOR);
		if (syncIdleConnectionMonitorThread != null) {
			syncIdleConnectionMonitorThread.interrupt();
		}

		// Closing the Async HTTP client (if running)
		CloseableHttpAsyncClient asyncClient = (CloseableHttpAsyncClient) options.getOption(Option.ASYNCHTTPCLIENT);
		if (asyncClient != null && asyncClient.isRunning()) {
			asyncClient.close();
		}

		AsyncIdleConnectionMonitorThread asyncMonitorThread = (AsyncIdleConnectionMonitorThread) options.getOption(Option.ASYNC_MONITOR);
		if (asyncMonitorThread != null) {
			asyncMonitorThread.interrupt();
		}
	}

	public GetRequest get(String url) {
		return new GetRequest(httpClientHelper, HttpMethod.GET, url);
	}

	public GetRequest head(String url) {
		return new GetRequest(httpClientHelper, HttpMethod.HEAD, url);
	}

	public HttpRequestWithBody options(String url) {
		return new HttpRequestWithBody(httpClientHelper, HttpMethod.OPTIONS, url);
	}

	public HttpRequestWithBody post(String url) {
		return new HttpRequestWithBody(httpClientHelper, HttpMethod.POST, url);
	}

	public HttpRequestWithBody delete(String url) {
		return new HttpRequestWithBody(httpClientHelper, HttpMethod.DELETE, url);
	}

	public HttpRequestWithBody patch(String url) {
		return new HttpRequestWithBody(httpClientHelper, HttpMethod.PATCH, url);
	}

	public HttpRequestWithBody put(String url) {
		return new HttpRequestWithBody(httpClientHelper, HttpMethod.PUT, url);
	}

}
