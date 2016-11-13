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

package net.ychron.unirestinst.http.async;

import com.mashape.unirest.http.exceptions.UnirestException;

import net.ychron.unirestinst.http.HttpClientHelper;
import net.ychron.unirestinst.http.HttpResponse;
import net.ychron.unirestinst.request.HttpRequest;

public class RequestThread<T> extends Thread {

	private HttpRequest httpRequest;
	private Class<T> responseClass;
	private Callback<T> callback;
	private HttpClientHelper httpClientHelper;

	public RequestThread(HttpClientHelper httpClientHelper, HttpRequest httpRequest, Class<T> responseClass, Callback<T> callback) {
		this.httpClientHelper = httpClientHelper;
		this.httpRequest = httpRequest;
		this.responseClass = responseClass;
		this.callback = callback;
	}

	@Override
	public void run() {
		HttpResponse<T> response;
		try {
			response = httpClientHelper.request(httpRequest, responseClass);
			if (callback != null) {
				callback.completed(response);
			}
		} catch (UnirestException e) {
			callback.failed(e);
		}
	}

}
