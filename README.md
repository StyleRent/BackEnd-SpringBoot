[![GitHub open issues](https://img.shields.io/github/issues-raw/StyleRent/BackEnd-SpringBoot)](https://github.com/StyleRent/BackEnd-SpringBoot/issues)
[![GitHub closed issues](https://img.shields.io/github/issues-closed-raw/StyleRent/BackEnd-SpringBoot)](https://github.com/StyleRent/BackEnd-SpringBoot/issues)
[![GitHub stars](https://img.shields.io/github/stars/StyleRent/BackEnd-SpringBoot)](https://github.com/Alisherka7/AttendanceServer/stargazers)
[![License](https://img.shields.io/github/license/StyleRent/BackEnd-SpringBoot)](./LICENSE)
<br>
![Spring](https://img.shields.io/badge/spring-%236DB33F.svg?style=for-the-badge&logo=spring&logoColor=white)
![Java](https://img.shields.io/badge/java-%23ED8B00.svg?style=for-the-badge&logo=java&logoColor=white)
![AWS](https://img.shields.io/badge/AWS-%23FF9900.svg?style=for-the-badge&logo=amazon-aws&logoColor=white)
![MySQL](https://img.shields.io/badge/mysql-%2300f.svg?style=for-the-badge&logo=mysql&logoColor=white)
<div align="center">
  <img src="https://user-images.githubusercontent.com/38793933/227194248-7c9a5eba-880e-47cc-a3ba-62d141f6b3e6.png" alt="logo" width="300px"/>
  
  <h1>STYLERENT - 옷 대여[백엔드]</h1>
  

<h4>
    <a href="https://github.com/StyleRent/BackEnd-SpringBoot">View Demo</a>
  <span> · </span>
    <a href="https://github.com/Alisherka7/QuizThis_RestfulAPI_SpringBoot/blob/main/README.md">Documentation</a>
  <span> · </span>
   <a href="https://github.com/StyleRent/FrontEnd-Android">모바일 앱 리포지토리</a>
  <span> · </span>
    <a href="https://github.com/StyleRent/BackEnd-SpringBoot/issues">Report Bug</a>
  <span> · </span>
    <a href="https://github.com/StyleRent/BackEnd-SpringBoot/issues">Request Feature</a>
  </h4>
</div>

<!-- 목차 -->
# :notebook_with_decorative_cover: 목차

- [About the Project](#book-documentaion)
  * [Screenshots](#camera-screenshots)
  * [Tech Stack](#space_invader-tech-stack)
  * [Features](#dart-features)
  * [Color Reference](#art-color-reference)
  * [Environment Variables](#key-environment-variables)
- [Documentation](#book-documentation)
  * [Installation](#gear-installation)
  * [Running Tests](#test_tube-running-tests)
  * [Run Locally](#running-run-locally)
  * [Deployment](#triangular_flag_on_post-deployment)
- [Usage](#eyes-usage)
- [Roadmap](#compass-roadmap)
- [Contributing](#wave-contributing)
  * [Code of Conduct](#scroll-code-of-conduct)
- [FAQ](#grey_question-faq)
- [License](#warning-license)
- [Contact](#handshake-contact)
- [Acknowledgements](#gem-acknowledgements)


## :book: Documentation

### :gear: Installation
* Java JDK -> 18.0.1
* Spring boot 3.0.5
* <a href="https://www.jetbrains.com/ko-kr/community/education/#students">Intellij Idea Ultimate Version!</a>
* <a href="https://www.apachefriends.org/">XAMPP Local Web Server</a>
* <a href="https://git-scm.com/">GIT</a>

### 프로젝트 설치 
1. 해당 리포지토리 프로젝트를 자신의 컴퓨터에서 다운받으세요. <br>
    1.1 <a href="https://github.com/StyleRent/BackEnd-SpringBoot/archive/refs/heads/main.zip">**압축 버전을 다운 받는 방법**</a> <br>
    1.2 CMD 또는 터미널을 열고 원하는 폴도에서 ```git clone https://github.com/StyleRent/BackEnd-SpringBoot.git``` 명령어로 해당 프로젝트를 다운 받으세요 <br>

2. XAMPP 웹 서버와 MySQL을 실행하세요.<br>
<img width="332" alt="Screen Shot 2023-03-31 at 9 12 44" src="https://user-images.githubusercontent.com/38793933/228991598-14a5e427-1a97-430e-ad14-33ba6d98a026.png">

  2.1 기준으로 Mysql은  ``` ttp://localhost:3306/ ``` 주소로 실행시킵니다. **Admin** 보튼 클릭으로 **PhpMyAdmin** 데이터베이스 관리 페이지로 넘어갑니다.
  
  <img width="445" alt="Screen Shot 2023-03-31 at 9 15 06" src="https://user-images.githubusercontent.com/38793933/228991820-89133485-3a40-40ee-b697-221159f12ce0.png">

   2.2 New 버튼으로 새로운 테이블 생성하세요.<br>
   <img width="558" alt="Screen Shot 2023-03-31 at 9 19 24" src="https://user-images.githubusercontent.com/38793933/228992276-0c7251eb-c7a5-48d2-875e-4a94c6183d6e.png">
   <br>
   2.3 테이블명 - "**stylerent**", user - **root**, 비밀번호 없습니다.!!


3. 프로젝트를 IntellijIdea로 열리세요.<br>
<img width="307" alt="Screen Shot 2023-03-31 at 9 07 53" src="https://user-images.githubusercontent.com/38793933/228991130-77482e2b-b7a7-4b63-97cb-ccc96235e57f.png">
<img width="295" alt="Screen Shot 2023-03-31 at 9 08 29" src="https://user-images.githubusercontent.com/38793933/228991202-cc766bc1-b327-439e-b1c4-0c9bbff65185.png">

3.1 열리신 프로젝트 Build가 끝나고 나서 스프링부트와 데이터베이스 연동을 하세요.!

<img width="241" alt="Screen Shot 2023-03-31 at 9 23 26" src="https://user-images.githubusercontent.com/38793933/228992738-0a2bfb44-07c0-4ac1-a33e-107718dc5d65.png">

3.2 스프링 부트 프로젝트의 설정 파일은 ``` Backend - SrpingBoot/src/resources/application.properties ```에 있습니다.

3.3 데이터베이스 연동 설정은 다음과 같습니다. <br>

<img width="560" alt="Screen Shot 2023-03-31 at 9 26 24" src="https://user-images.githubusercontent.com/38793933/228993007-37681ba5-5a0d-431a-b2a4-9acdd9d7d799.png">

4. 프로젝트 설정 과정은 끝났습니다. 

<img width="413" alt="Screen Shot 2023-03-31 at 9 28 40" src="https://user-images.githubusercontent.com/38793933/228993237-afe1acab-6948-4b5d-a79d-1bd14768e732.png">
