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

package views

import models.NormalMode
import navigation.Navigator
import pages.UpdateYourEmployerPage
import views.behaviours.ViewBehaviours
import views.html.UpdateYourEmployerInformationView

class UpdateYourEmployerInformationViewSpec extends ViewBehaviours {

  "UpdateYourEmployerInformation view" must {

    val application = applicationBuilder(userAnswers = Some(emptyUserAnswers)).build()

    val view = application.injector.instanceOf[UpdateYourEmployerInformationView]

    val applyView = view.apply(navigator.nextPage(UpdateYourEmployerPage, NormalMode, emptyUserAnswers).url)(fakeRequest, messages)

    behave like normalPage(applyView, "updateYourEmployerInformation")

    behave like pageWithBackLink(applyView)
  }
}
