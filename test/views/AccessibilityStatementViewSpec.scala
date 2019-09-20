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

import views.behaviours.ViewBehaviours
import views.html.AccessibilityStatementView

class AccessibilityStatementViewSpec extends ViewBehaviours {

  val application = applicationBuilder(userAnswers = Some(emptyUserAnswers)).build()
  val view = application.injector.instanceOf[AccessibilityStatementView]
  val applyView = view.apply("", "")(fakeRequest, messages)

  "AccessibilityStatement view" must {

    val introductionLink = s"""<a href="${frontendAppConfig.accessibilityStatementUrl}">${messages("accessibilityStatement.introduction.paragraph2.linkText")}</a>"""
    val serviceLink = s"""<a href=@serviceUrl>@subDomain</a>}"""
    val usingThisServiceLink = s"""<a href="${frontendAppConfig.accessibilityStatementUrl}">${messages("accessibilityStatement.usingThisService.paragraph3.linkText")}</a>"""
    val howAccessibleThisServiceIsLink = s"""<a href="${frontendAppConfig.w3StandardsUrl}">${messages("accessibilityStatement.howAccessibleThisServiceIs.paragraph1.linkText")}</a>"""
    val reportingAccessibilityProblemsWithThisServiceLink = s"""<a href="${frontendAppConfig.reportAProblemNonJSUrl}">${messages("accessibilityStatement.reportingAccessibilityProblemsWithThisService.paragraph1.linkText")}</a>"""
    val whatToDoIfYouAreNotHappyWithHowWeRespondToYourComplaintLink1 = s"""<a href="${frontendAppConfig.equalityAdvisoryServiceUrl}">${messages("accessibilityStatement.whatToDoIfYouAreNotHappyWithHowWeRespondToYourComplaint.paragraph1.linkText1")}</a>"""
    val whatToDoIfYouAreNotHappyWithHowWeRespondToYourComplaintLink2 = s"""<a href="${frontendAppConfig.equalityNIUrl}">${messages("accessibilityStatement.whatToDoIfYouAreNotHappyWithHowWeRespondToYourComplaint.paragraph1.linkText2")}</a>"""
    val contactUsLink = s"""<a href="${frontendAppConfig.dealingHmrcAdditionalNeedsUrl}">${messages("accessibilityStatement.contactingUsByPhoneOrGettingAVisitFromUsInPerson.paragraph3.linkText")}</a>"""
    val technicalInformationLink = s"""<a href="${frontendAppConfig.w3StandardsUrl}">${messages("accessibilityStatement.technicalInformationAboutThisServicesAccessibility.paragraph2.linkText")}</a>"""
    val dacLink = s"""<a href="${frontendAppConfig.dacUrl}">${messages("accessibilityStatement.howWeTestedThisService.paragraph2.linkText")}</a>"""

    behave like normalPage(applyView, "accessibilityStatement")

    behave like pageWithBodyText(applyView,
      messages("accessibilityStatement.title", messages("site.service_name")),
      messages("accessibilityStatement.heading", messages("site.service_name")),
      "accessibilityStatement.introduction.paragraph1",
      messages("accessibilityStatement.introduction.paragraph2", introductionLink),
      messages("accessibilityStatement.introduction.paragraph3", messages("site.service_name"), ""),
      "accessibilityStatement.usingThisService.heading",
      //TODO "accessibilityStatement.usingThisService.aboutTheService",
      "accessibilityStatement.usingThisService.paragraph1",
      "accessibilityStatement.usingThisService.listItem1",
      "accessibilityStatement.usingThisService.listItem2",
      "accessibilityStatement.usingThisService.listItem3",
      "accessibilityStatement.usingThisService.listItem4",
      "accessibilityStatement.usingThisService.listItem5",
      "accessibilityStatement.usingThisService.paragraph2",
      messages("accessibilityStatement.usingThisService.paragraph3", usingThisServiceLink),
      "accessibilityStatement.howAccessibleThisServiceIs.heading",
      messages("accessibilityStatement.howAccessibleThisServiceIs.paragraph1", howAccessibleThisServiceIsLink),
      "accessibilityStatement.whatToDoIfYouHaveDifficultyUsingThisService.heading",
      "accessibilityStatement.reportingAccessibilityProblemsWithThisService.heading",
      messages("accessibilityStatement.reportingAccessibilityProblemsWithThisService.paragraph1", reportingAccessibilityProblemsWithThisServiceLink),
      "accessibilityStatement.whatToDoIfYouAreNotHappyWithHowWeRespondToYourComplaint.heading",
      messages("accessibilityStatement.whatToDoIfYouAreNotHappyWithHowWeRespondToYourComplaint.paragraph1", whatToDoIfYouAreNotHappyWithHowWeRespondToYourComplaintLink1, whatToDoIfYouAreNotHappyWithHowWeRespondToYourComplaintLink2),
      "accessibilityStatement.contactingUsByPhoneOrGettingAVisitFromUsInPerson.heading",
      "accessibilityStatement.contactingUsByPhoneOrGettingAVisitFromUsInPerson.paragraph1",
      "accessibilityStatement.contactingUsByPhoneOrGettingAVisitFromUsInPerson.paragraph2",
      messages("accessibilityStatement.contactingUsByPhoneOrGettingAVisitFromUsInPerson.paragraph3", contactUsLink),
      "accessibilityStatement.technicalInformationAboutThisServicesAccessibility.heading",
      "accessibilityStatement.technicalInformationAboutThisServicesAccessibility.paragraph1",
      messages("accessibilityStatement.technicalInformationAboutThisServicesAccessibility.compliant", howAccessibleThisServiceIsLink),
      //messages("accessibilityStatement.technicalInformationAboutThisServicesAccessibility.nonCompliant", technicalInformationLink),
      //messages("accessibilityStatement.technicalInformationAboutThisServicesAccessibility.nonCompliant.heading", technicalInformationLink),
      //"accessibilityStatement.technicalInformationAboutThisServicesAccessibility.nonCompliant.paragraph1",
      //"accessibilityStatement.technicalInformationAboutThisServicesAccessibility.nonCompliant.subHeading",
      "accessibilityStatement.howWeTestedThisService.heading",
      messages("accessibilityStatement.howWeTestedThisService.paragraph1", frontendAppConfig.accessibilityStatementLastTested),
      messages("accessibilityStatement.howWeTestedThisService.paragraph2", dacLink),
      messages("accessibilityStatement.howWeTestedThisService.paragraph3", frontendAppConfig.accessibilityStatementLastTested, frontendAppConfig.accessibilityStatementFirstPublished)
    )

    behave like pageWithBackLink(applyView)
  }

  application.stop()
}