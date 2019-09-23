/*
 * Copyright 2019 HM Revenue & Customs
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package controllers

import base.SpecBase
import forms.TaxYearSelectionFormProvider
import generators.Generators
import models.TaxYearSelection._
import models.{NormalMode, TaxYearSelection}
import navigation.{FakeNavigator, Navigator}
import org.mockito.Matchers._
import org.mockito.Mockito.{reset, when}
import org.scalatest.BeforeAndAfterEach
import org.scalatest.concurrent.{IntegrationPatience, ScalaFutures}
import org.scalatest.mockito.MockitoSugar
import play.api.inject.bind
import play.api.mvc.{AnyContentAsFormUrlEncoded, Call}
import play.api.test.FakeRequest
import play.api.test.Helpers._
import repositories.SessionRepository
import services.TaiService
import views.html.TaxYearSelectionView

import scala.concurrent.Future
import org.scalacheck.Arbitrary.arbitrary
import org.scalacheck.Gen._
import org.scalatest.prop.PropertyChecks


class TaxYearSelectionControllerSpec extends SpecBase with PropertyChecks with Generators with MockitoSugar
  with ScalaFutures with IntegrationPatience with BeforeAndAfterEach {

  private val mockSessionRepository: SessionRepository = mock[SessionRepository]
  override def beforeEach(): Unit = {
    reset(mockSessionRepository)
  }

  def onwardRoute = Call("GET", "/foo")

  lazy val taxYearSelectionRoute = routes.TaxYearSelectionController.onPageLoad(NormalMode).url

  val form = new TaxYearSelectionFormProvider()()

  private val mockTaiService = mock[TaiService]

  "TaxYearSelection Controller" must {

    "onPageLoad" must {
      "return OK and the correct view for a GET" in {

        val application = applicationBuilder(userAnswers = Some(emptyUserAnswers)).build()

        val request = FakeRequest(GET, taxYearSelectionRoute)

        val result = route(application, request).value

        val view = application.injector.instanceOf[TaxYearSelectionView]

        status(result) mustEqual OK

        contentAsString(result) mustEqual
          view(form, NormalMode)(fakeRequest, messages).toString

        application.stop()
      }

      "populate the view correctly on a GET when the question has previously been answered" in {

        val application = applicationBuilder(userAnswers = Some(userAnswersCurrentAndPrevious)).build()

        val request = FakeRequest(GET, taxYearSelectionRoute)

        val view = application.injector.instanceOf[TaxYearSelectionView]

        val result = route(application, request).value

        status(result) mustEqual OK

        contentAsString(result) mustEqual
          view(form.fill(Seq(CurrentYear, CurrentYearMinus1)), NormalMode)(fakeRequest, messages).toString

        application.stop()
      }

      "redirect to Session Expired for a GET if no existing data is found" in {

        val application = applicationBuilder(userAnswers = None).build()

        val request = FakeRequest(GET, taxYearSelectionRoute)

        val result = route(application, request).value

        status(result) mustEqual SEE_OTHER
        redirectLocation(result).value mustEqual routes.SessionExpiredController.onPageLoad().url

        application.stop()
      }
    }

    "onPageLoad" must {
      "redirect to the next page for a POST when valid data is submitted" in {
        val application =
          applicationBuilder(userAnswers = Some(emptyUserAnswers))
            .overrides(bind[Navigator].toInstance(new FakeNavigator(onwardRoute)))
            .overrides(bind[TaiService].toInstance(mockTaiService))
            .overrides(bind[SessionRepository].toInstance(mockSessionRepository))
            .build()

        when(mockSessionRepository.set(any())).thenReturn(Future.successful(true))

        forAll(arbitrary[TaxYearSelection], choose(0, 2500)) {
          case (taxYearSelection, amount) =>

            when(mockTaiService.getPsubAmount(any(), any())(any(), any()))
              .thenReturn(Future.successful(Map(getTaxYear(taxYearSelection) -> amount)))

            val request =
              FakeRequest(POST, taxYearSelectionRoute)
                .withFormUrlEncodedBody(("value[0]", taxYearSelection.toString))

            val result = route(application, request).value

            status(result) mustEqual SEE_OTHER

            redirectLocation(result).value mustEqual onwardRoute.url

            reset(mockTaiService)
        }

        application.stop()
      }

      "return a Bad Request and errors for a POST when invalid data is submitted" in {
        val nonValidUserInputGen =
          arbitrary[String]
            .suchThat(!TaxYearSelection.values.map(_.toString).toSet.contains(_))

        val application = applicationBuilder(userAnswers = Some(emptyUserAnswers)).build()

        forAll(nonValidUserInputGen) {
          userInput =>

            val request =
              FakeRequest(POST, taxYearSelectionRoute)
                .withFormUrlEncodedBody(("value", userInput))

            val failedBoundForm = form.bind(Map("value" -> userInput))

            val view = application.injector.instanceOf[TaxYearSelectionView]

            val result = route(application, request).value

            status(result) mustEqual BAD_REQUEST

            contentAsString(result) mustEqual view(failedBoundForm, NormalMode)(fakeRequest, messages).toString

        }

        application.stop()
      }

      "redirect to Session Expired for a POST if no existing data is found" in {

        val application = applicationBuilder(userAnswers = None).build()

        forAll(arbitrary[TaxYearSelection]) {
          taxYearSelection =>

            val request =
              FakeRequest(POST, taxYearSelectionRoute)
                .withFormUrlEncodedBody(("value[0]", taxYearSelection.toString))

            val result = route(application, request).value

            status(result) mustEqual SEE_OTHER

            redirectLocation(result).value mustEqual routes.SessionExpiredController.onPageLoad().url

            reset(mockTaiService)
        }

        application.stop()
      }
    }
  }
}
