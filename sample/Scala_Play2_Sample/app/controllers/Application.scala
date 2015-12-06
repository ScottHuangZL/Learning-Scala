//package controllers
//
//import play.api._
//import play.api.mvc._
//


package controllers


import java.io.File
import models.Artist
import models.Task
import play.api.libs.json.JsValue
import play.api.mvc._
import play.twirl.api.Html

import play.api.Play.current
import play.api.i18n.Messages.Implicits._

import play.api.mvc.{Flash, Action, Controller}
import play.api.i18n.{Messages, MessagesApi, I18nSupport}


class Application extends Controller {
  def index = Action {
    implicit request =>
      Redirect(routes.Products.list())
  }


}


class TaskController extends Controller {
  def index = Action {
    implicit request =>
      Redirect(routes.TaskController.tasks)
  }

  def tasks = Action {
    implicit request =>
      Ok(views.html.index(Task.all))
  }

  def newTask = Action(parse.urlFormEncoded) {
    implicit request =>
      Task.add(request.body.get("taskName").get.head)
      Redirect(routes.TaskController.tasks)
  }

  def deleteTask(id: Int) = Action {
    implicit request =>
      Task.delete(id)
      Ok
  }
}

class API extends Controller {

  def listArtist = Action {
    implicit request =>
      Ok(views.html.home(Artist.fetch))
  }

  def fetchArtistByName(name: String) = Action {
    implicit request =>
      Ok(views.html.home(Artist.fetchByName(name)))
  }

  def search(name: String, country: String) = Action {
    implicit request =>
      val result = Artist.fetchByNameOrCountry(name, country)
      if (result.isEmpty) {
        NoContent
      }
      else {
        Ok(views.html.home(result))
      }
  }

  def search2(name: Option[String], country: String) = Action {
    implicit request =>
      val result = name match {
        case Some(n) => Artist.fetchByNameOrCountry(n, country)
        case None => Artist.fetchByCountry(country)
      }
      if (result.isEmpty) {
        NoContent
      }
      else {
        Ok(views.html.home(result))
      }
  }
}


class AppController extends Controller {
  def subscribe = Action(parse.json) {
    request =>
      val reqData: JsValue = request.body
      val emailId = (reqData \ "emailId").as[String]
      val interval = (reqData \ "interval").as[String]
      Ok(s"added $emailId to subscriber's list and will send updates every $interval")
  }

  //  def createProfile = Action(parse.multipartFormData) {
  //    request =>
  //      val formData = request.body.asFormUrlEncoded
  //      val email: String = formData.get("email").get(0)
  //      val name: String = formData.get("name").get(0)
  //      val userId: Long = User(email, name).save
  //      request.body.file("displayPic").map {
  //        picture =>
  //          val path = "/socialize/user/"
  //          if (!picture.filename.isEmpty) {
  //            picture.ref.moveTo(new File(path + userId + ".jpeg"))
  //          }
  //          Ok("successfully added user")
  //      }.getOrElse {
  //        BadRequest("failed to add user")
  //      }
  //  }
}