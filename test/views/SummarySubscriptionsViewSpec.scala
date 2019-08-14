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
import models.TaxYearSelection.{CurrentYear, CurrentYearMinus1, getTaxYear}
import models.PSubsByYear.formats
import pages.SummarySubscriptionsPage
import views.behaviours.{SummarySubscriptionComponentBehaviours, ViewBehaviours}
import views.html.SummarySubscriptionsView

class SummarySubscriptionsViewSpec extends ViewBehaviours with SummarySubscriptionComponentBehaviours {

  "SummarySubscriptions view" must {

    val messageKeyPrefix = "summarySubscriptions"

    val application = applicationBuilder(userAnswers = Some(someUserAnswers)).build()

    val view = application.injector.instanceOf[SummarySubscriptionsView]

    val subscriptions = someUserAnswers.get(SummarySubscriptionsPage).get

    val npsData = Map(
      getTaxYear(CurrentYear) -> 300,
      getTaxYear(CurrentYearMinus1) -> 0)

    val applyView = view.apply(
      subscriptions = subscriptions,
      npsData = npsData,
      nextPageUrl = navigator.nextPage(SummarySubscriptionsPage, NormalMode, someUserAnswers).url,
      mode = NormalMode
    )(fakeRequest, messages)

    application.stop

    behave like normalPage(applyView, messageKeyPrefix)

    behave like pageWithBackLink(applyView)

    behave like pageWithSummarySubscriptionComponent(view, messageKeyPrefix)
  }
}
