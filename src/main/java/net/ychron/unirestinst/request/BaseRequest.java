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

package net.ychron.unirestinst.request;

import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.exceptions.UnirestException;

import net.ychron.unirestinst.http.HttpClientHelper;
import net.ychron.unirestinst.http.HttpResponse;
import net.ychron.unirestinst.http.async.Callback;
import net.ychron.unirestinst.http.options.Options;

import java.io.InputStream;
import java.util.concurrent.Future;

public abstract class BaseRequest {

	protected static final String UTF_8 = "UTF-8";

	protected HttpRequest httpRequest;

	Options options;
	HttpClientHelper httpClientHelper;
	
	protected BaseRequest(HttpClientHelper httpClientHelper) {
		this.httpClientHelper = httpClientHelper;
	}
	
	protected BaseRequest(HttpClientHelper httpClientHelper, HttpRequest httpRequest) {
		this.httpClientHelper = httpClientHelper;
		this.httpRequest = httpRequest;
	}

	public HttpRequest getHttpRequest() {
		return this.httpRequest;
	}

	protected BaseRequest() {
		super();
	}

	public HttpResponse<String> asString() throws UnirestException {
		return httpClientHelper.request(httpRequest, String.class);
	}

	public Future<HttpResponse<String>> asStringAsync() {
		return httpClientHelper.requestAsync(httpRequest, String.class, null);
	}

	public Future<HttpResponse<String>> asStringAsync(Callback<String> callback) {
		return httpClientHelper.requestAsync(httpRequest, String.class, callback);
	}

	public HttpResponse<JsonNode> asJson() throws UnirestException {
		return httpClientHelper.request(httpRequest, JsonNode.class);
	}

	public Future<HttpResponse<JsonNode>> asJsonAsync() {
		return httpClientHelper.requestAsync(httpRequest, JsonNode.class, null);
	}

	public Future<HttpResponse<JsonNode>> asJsonAsync(Callback<JsonNode> callback) {
		return httpClientHelper.requestAsync(httpRequest, JsonNode.class, callback);
	}

	public <T> HttpResponse<T> asObject(Class<? extends T> responseClass) throws UnirestException {
		return httpClientHelper.request(httpRequest, (Class) responseClass);
	}

	public <T> Future<HttpResponse<T>> asObjectAsync(Class<? extends T> responseClass) {
		return httpClientHelper.requestAsync(httpRequest, (Class) responseClass, null);
	}

	public <T> Future<HttpResponse<T>> asObjectAsync(Class<? extends T> responseClass, Callback<T> callback) {
		return httpClientHelper.requestAsync(httpRequest, (Class) responseClass, callback);
	}

	public HttpResponse<InputStream> asBinary() throws UnirestException {
		return httpClientHelper.request(httpRequest, InputStream.class);
	}

	public Future<HttpResponse<InputStream>> asBinaryAsync() {
		return httpClientHelper.requestAsync(httpRequest, InputStream.class, null);
	}

	public Future<HttpResponse<InputStream>> asBinaryAsync(Callback<InputStream> callback) {
		return httpClientHelper.requestAsync(httpRequest, InputStream.class, callback);
	}

}
