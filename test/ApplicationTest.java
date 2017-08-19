import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static play.mvc.Http.Status.OK;
import static play.test.Helpers.*;

import play.mvc.Http.RequestBuilder;

import controllers.HomeController;

import java.util.ArrayList;

import com.google.common.collect.ImmutableMap;
import org.junit.Test;

import play.Application;
import play.inject.guice.GuiceApplicationBuilder;
import play.mvc.Result;
import play.test.Helpers;
import play.test.WithApplication;
import play.twirl.api.Content;
import play.mvc.Call;


import play.http.HttpEntity;

public class ApplicationTest extends WithApplication {

  @Test
  public void testIndex() {
     Helpers.running(Helpers.fakeApplication(), () -> {
          RequestBuilder mockActionRequest = Helpers.fakeRequest(
               controllers.routes.HomeController.index());
          Result result = Helpers.route(mockActionRequest);
          assertEquals(OK, result.status());
     });
  }

  @Test
  public void testIndexGetJson() {
     Helpers.running(Helpers.fakeApplication(), () -> {
          RequestBuilder mockActionRequest = Helpers.fakeRequest(
               controllers.routes.HomeController.index()).method("GET").header("Accept","application/json");
          Result result = Helpers.route(mockActionRequest);
	  String responseBody = ((HttpEntity.Strict)result.body()).data().decodeString("US-ASCII");

          assertEquals("{\"message\": \"Good morning\"}", responseBody);
     });
  }

  @Test
  public void testIndexGetNoAccept() {
     Helpers.running(Helpers.fakeApplication(), () -> {
          RequestBuilder mockActionRequest = Helpers.fakeRequest(
               controllers.routes.HomeController.index()).method("GET");
          Result result = Helpers.route(mockActionRequest);
	  String responseBody = ((HttpEntity.Strict)result.body()).data().decodeString("US-ASCII");
          assertEquals("<p>Hello, World</p>", responseBody);
     });
  }

  @Test
  public void testIndexGetBadAccept() {
     Helpers.running(Helpers.fakeApplication(), () -> {
          RequestBuilder mockActionRequest = Helpers.fakeRequest(
               controllers.routes.HomeController.index()).method("GET").header("Accept","application/xson");
          Result result = Helpers.route(mockActionRequest);
	  String responseBody = ((HttpEntity.Strict)result.body()).data().decodeString("US-ASCII");
          assertEquals("<p>Hello, World</p>", responseBody);
     });
  }

  @Test
  public void testIndexPostNoAccept() {
     Helpers.running(Helpers.fakeApplication(), () -> {
          RequestBuilder mockActionRequest = Helpers.fakeRequest(
               controllers.routes.HomeController.index()).method("POST");
          Result result = Helpers.route(mockActionRequest);
	  String responseBody = ((HttpEntity.Strict)result.body()).data().decodeString("US-ASCII");
          assertEquals("", responseBody);
     });
  }

  @Test
  public void testIndexPostAccept() {
     Helpers.running(Helpers.fakeApplication(), () -> {
          RequestBuilder mockActionRequest = Helpers.fakeRequest(
               controllers.routes.HomeController.index()).method("POST").header("Accept","application/json");
          Result result = Helpers.route(mockActionRequest);
	  String responseBody = ((HttpEntity.Strict)result.body()).data().decodeString("US-ASCII");
          assertEquals("", responseBody);
     });
  }

  @Test
  public void testIndexPostBadAccept() {
     Helpers.running(Helpers.fakeApplication(), () -> {
          RequestBuilder mockActionRequest = Helpers.fakeRequest(
               controllers.routes.HomeController.index()).method("POST").header("Accept","application/jzon");
          Result result = Helpers.route(mockActionRequest);
	  String responseBody = ((HttpEntity.Strict)result.body()).data().decodeString("US-ASCII");
          assertEquals("", responseBody);
     });
  }
}
