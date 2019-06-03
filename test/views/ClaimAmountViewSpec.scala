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

import models.{EnglishRate, NormalMode, Rates, ScottishRate}
import navigation.Navigator
import org.scalatest.mockito.MockitoSugar
import pages.ClaimAmountPage
import play.twirl.api.HtmlFormat
import services.ClaimAmountService
import views.behaviours.ViewBehaviours
import views.html.ClaimAmountView


class ClaimAmountViewSpec extends ViewBehaviours with MockitoSugar {


  private val nav = new Navigator

  "ClaimAmount view" must {

    val application = applicationBuilder(userAnswers = Some(emptyUserAnswers)).build()

    val view = application.injector.instanceOf[ClaimAmountView]

    val claimAmount = 100

    val claimAmountService = application.injector.instanceOf[ClaimAmountService]

    def englishRate = EnglishRate(
      basicRate = frontendAppConfig.englishBasicRate,
      higherRate = frontendAppConfig.englishHigherRate,
      calculatedBasicRate = claimAmountService.calculateTax(frontendAppConfig.englishBasicRate, claimAmount),
      calculatedHigherRate = claimAmountService.calculateTax(frontendAppConfig.englishHigherRate, claimAmount)
    )

    def scottishRate = ScottishRate(
      starterRate = frontendAppConfig.scottishStarterRate,
      basicRate = frontendAppConfig.scottishBasicRate,
      higherRate = frontendAppConfig.scottishHigherRate,
      calculatedStarterRate = claimAmountService.calculateTax(frontendAppConfig.scottishStarterRate, claimAmount),
      calculatedBasicRate = claimAmountService.calculateTax(frontendAppConfig.scottishBasicRate, claimAmount),
      calculatedHigherRate = claimAmountService.calculateTax(frontendAppConfig.scottishHigherRate, claimAmount)
    )

    def applyView(rates: Seq[Rates]): HtmlFormat.Appendable =
      view.apply(
        nextPageUrl = nav.nextPage(ClaimAmountPage, NormalMode, emptyUserAnswers).url,
        claimAmountAndAnyDeductions = 100,
        subscriptionAmount = 100,
        expensesEmployerPaid = None,
        employerContribution = None,
        rates
      )(fakeRequest, messages)

    behave like normalPage(applyView(Seq(englishRate, scottishRate)), "claimAmount")

    behave like pageWithBackLink(applyView(Seq(englishRate, scottishRate)))

    "Display correct content" when {

      "employer has made a contribution and no tax valid tax code is found" in {

        val doc = asDocument(applyView(Seq(englishRate, scottishRate)))

        assertContainsMessages(doc,
          "claimAmount.title",
          "claimAmount.heading",
          messages("claimAmount.claimAmount", claimAmount),
          "claimAmount.claimAmountDescription",
          "claimAmount.englandHeading",
          messages(
            "claimAmount.basicRate",
            englishRate.calculatedBasicRate,
            claimAmount,
            englishRate.basicRate
          ),
          messages(
            "claimAmount.higherRate",
            englishRate.calculatedHigherRate,
            claimAmount,
            englishRate.higherRate
          ),
          "claimAmount.scotlandHeading",
          messages(
            "claimAmount.starterRate",
            scottishRate.calculatedStarterRate,
            claimAmount,
            scottishRate.calculatedStarterRate
          ),
          messages("claimAmount.basicRate",
            scottishRate.calculatedBasicRate,
            claimAmount,
            scottishRate.calculatedBasicRate
          ),
          messages("claimAmount.higherRate",
            scottishRate.calculatedHigherRate,
            claimAmount,
            scottishRate.calculatedHigherRate
          )
        )
      }

      "a valid English tax code is found" in {

        val doc = asDocument(applyView(Seq(englishRate)))

        assertContainsMessages(doc,
          "claimAmount.title",
          "claimAmount.heading",
          messages("claimAmount.claimAmount", claimAmount),
          "claimAmount.claimAmountDescription",
          messages(
            "claimAmount.basicRate",
            englishRate.calculatedBasicRate,
            claimAmount,
            englishRate.basicRate
          ),
          messages(
            "claimAmount.higherRate",
            englishRate.calculatedHigherRate,
            claimAmount,
            englishRate.higherRate
          )
        )

        assertDoesntContainMessages(doc,
          "claimAmount.englandHeading",
          "claimAmount.scotlandHeading",
          messages(
            "claimAmount.starterRate",
            scottishRate.calculatedStarterRate,
            claimAmount,
            scottishRate.calculatedStarterRate
          ),
          messages(
            "claimAmount.higherRate",
            scottishRate.calculatedHigherRate,
            claimAmount,
            scottishRate.calculatedHigherRate
          )
        )
      }

      "a valid Scottish tax code is found" in {

        val doc = asDocument(applyView(Seq(scottishRate)))

        assertContainsMessages(doc,
          "claimAmount.title",
          "claimAmount.heading",
          messages("claimAmount.claimAmount", claimAmount),
          "claimAmount.claimAmountDescription",
          messages(
            "claimAmount.starterRate",
            scottishRate.calculatedStarterRate,
            claimAmount,
            scottishRate.calculatedStarterRate
          ),
          messages("claimAmount.basicRate",
            scottishRate.calculatedBasicRate,
            claimAmount,
            scottishRate.calculatedBasicRate
          ),
          messages("claimAmount.higherRate",
            scottishRate.calculatedHigherRate,
            claimAmount,
            scottishRate.calculatedHigherRate
          )
        )

        assertDoesntContainMessages(doc,
          "claimAmount.englandHeading",
          messages(
            "claimAmount.higherRate",
            englishRate.calculatedHigherRate,
            claimAmount,
            englishRate.higherRate
          ),
          "claimAmount.scotlandHeading"
        )
      }
    }
  }

}
