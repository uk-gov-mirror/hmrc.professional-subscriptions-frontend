/*
 * Copyright 2020 HM Revenue & Customs
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

import controllers.routes
import forms.DuplicateClaimForOtherYearsFormProvider
import models.NormalMode
import play.api.data.Form
import play.twirl.api.HtmlFormat
import views.behaviours.YesNoViewBehaviours
import views.html.DuplicateClaimForOtherYearsView

class DuplicateClaimForOtherYearsViewSpec extends YesNoViewBehaviours {

  val messageKeyPrefix = "duplicateClaimForOtherYears"

  val form = new DuplicateClaimForOtherYearsFormProvider()()

  "DuplicateClaimForOtherYears view" must {

    val application = applicationBuilder(userAnswers = Some(emptyUserAnswers)).build()

    val view = application.injector.instanceOf[DuplicateClaimForOtherYearsView]


    def applyView(form: Form[_]): HtmlFormat.Appendable =
      view.apply(form, NormalMode, taxYear, index)(fakeRequest, messages)

    application.stop()

    behave like normalPage(applyView(form), messageKeyPrefix)

    behave like pageWithBackLink(applyView(form))

    behave like yesNoPage(form, applyView, messageKeyPrefix, routes.DuplicateClaimForOtherYearsController.onSubmit(NormalMode, taxYear, index).url)
  }
}
