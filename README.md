<br>

<div align='center'>
  <image src="https://user-images.githubusercontent.com/71180414/147133698-5755989d-1067-4cd5-8661-eb3dd57e2eef.png" width="300"/>
  <br/>
  <image src="https://img.shields.io/website?down_message=DOWN&up_message=UP&label=server-prod&url=https://api.devnity.xyz/health"/>  
  <image src="https://img.shields.io/website?down_message=DOWN&up_message=UP&label=server-dev&url=http://3.37.54.135:8888/health"/>
  <image href="https://codecov.io/gh/prgrms-web-devcourse/Team_BbungCles_Devnity_BE" src="https://codecov.io/gh/prgrms-web-devcourse/Team_BbungCles_Devnity_BE/branch/develop/graph/badge.svg?token=MX7YVUP5SW"/>
  <br/><br/>

  <image width="50" src="https://user-images.githubusercontent.com/71180414/147135209-7fd39773-d634-4c06-a9d2-dd1052ea848a.png"/>
  <b> 데브코스 커뮤니티 플랫폼 'Devnity' 백엔드</b>
  
</div>

<br>

## 🔗 프로젝트 관련 링크

> 배포 사이트 링크 : https://devnity.xyz   

- [개발서버 API 문서 (RestDocs)](http://3.37.54.135:8888/docs/index.html)
- [배포서버 API 문서 (RestDocs)](https://api.devnity.xyz/docs/index.html)
- [프로젝트 Wiki](https://github.com/prgrms-web-devcourse/Team_BbungCles_Devnity_BE/wiki)
- [프로젝트 노션](https://www.notion.so/backend-devcourse/7-Devnity-c0f09e027acf4d9e8aeab21f7a9a8822)
- [프론트엔드 Repo](https://github.com/prgrms-web-devcourse/Team_BbungCles_Devnity_FE)

<br>

## 🙉 팀 소개

<table>
  <tr>
    <td align="center"><a href="https://github.com/jerimo"><img src="https://avatars.githubusercontent.com/u/15929412?v=4" width="150px" /></a></td>
    <td align="center"><a href="https://github.com/hanjo8813"><img src="https://avatars.githubusercontent.com/u/71180414?v=4" width="150px" /></a></td>
    <td align="center"><a href="https://github.com/seung-hun-h"><img src="https://avatars.githubusercontent.com/u/60502370?v=4" width="150px" /></a></td>
  </tr>
  <tr>
    <td align="center"><b>Jerry</b></td>
    <td align="center"><b>Hanjo</b></td>
    <td align="center"><b>Mark</b></td>
  </tr>
  <tr>
    <td align="center">알고리즘 천재(플레 1)</td>
    <td align="center">데브옵스 개발자(?)</td>
    <td align="center">든든한 객체지향맨</td>
  </tr>
</table>

<details>
  <summary><b>백엔드는 이렇게 협업해요!</b></summary>
  <hr/>
  <h3>브랜치 전략</h3>
  <ul>
    <li><b>Git Flow</b> 전략을 사용합니다.</li>
    <li>기존 Git Flow 전략을 간소화해 4가지 브랜치를 사용합니다.</li>
    <ul>
      <li><code>main</code> : 최종 배포 서버 브랜치</li>
      <li><code>develop</code> : 개발 서버 브랜치</li>
      <li><code>feature</code> : 기능 구현 브랜치</li>
      <li><code>hotfix</code> : 핫픽스 브랜치</li>
    </ul>
    <li><b>1Task == 1Issue == 1PR</b> 규칙을 따랐습니다.</li>
  </ul>
  
  <h3>즉각 코드리뷰 및 피드백 반영</h3>
  <ul>
    <li>저희는 코드 품질과 컨벤션 통일을 위해 지속적인 코드리뷰를 지향하였습니다.</li>
    <li>PR이 생성되면 누군가 한명이 코드리뷰를 작성해야만 merge가 승인됩니다.</li>
  </ul>
  
  <h3>초단기 스프린트</h3>
  <ul>
    <li>2주간 총 4번의 초단기 스프린트를 진행했습니다. (3~4일 간격)</li>
    <li>
      스프린트 시작 전 작업할 태스크를 모두 <code>Issue</code>로 등록한 후, <code>Milestone</code>을 설정하여 진행 목표를 정했습니다.
      <a href="https://github.com/prgrms-web-devcourse/Team_BbungCles_Devnity_BE/projects?query=is%3Aclosed">(참고)</a>
    </li>
    <li>
      스프린트 기간엔 Github의 <code>Project</code> 칸반보드를 활용하여 개인 태스크 진행현황을 공유하였습니다.
      <a href="https://github.com/prgrms-web-devcourse/Team_BbungCles_Devnity_BE/milestones?state=closed">(참고)</a>
    </li>
    <li>
      스프린트가 종료되면 간단 회고록을 작성하였습니다.
      <a href="https://github.com/prgrms-web-devcourse/Team_BbungCles_Devnity_BE/wiki">(참고)</a>
    </li>
  </ul>
  
  <hr/>
</details>

<br>

## 💻 프로젝트 소개

<div align='center'>
  <image width="850" src="https://user-images.githubusercontent.com/71180414/147160948-721c6960-e793-463d-9241-da479df1a133.png"/>

  <h3>Devnity는 프로그래머스 데브코스 수강생을 위한 커뮤니티 플랫폼입니다.</h3>
  <p>데브코스 1기 백엔드와 프론트엔드 수강생들간 소통의 불편함을 해소하고자 만들어졌습니다.</p>
  <p>데브코스 과정 초반에 활용될 자기소개를 적는 기능과, 각 수강생의 위치를 파악할 수 있는 기능을 제공합니다.</p>
  <p>또한 맵 API를 활용한 모각코 모집, 스터디나 동아리, 토이프로젝트를 모집할 수 있는 기능을 제공합니다.</p>
</div>

<br>

## 🔎 기능 소개
> 항목을 클릭하여 자세히 볼 수 있습니다.

<br>

<details>
  <summary><b>관리자</b></summary>
  <br>
  
| ![SRDT6VqQmL](https://user-images.githubusercontent.com/71180414/147162078-948032fb-c6db-4fca-b66c-b9e100c18694.gif)|
|:--:|
|**초대 링크 생성**|

  - 
  - ㅇㅇ
  

</details>

<br>

<details>
<summary><b>회원가입 & 로그인</b></summary>
<br>
</details>

<br>

<details>
<summary><b>메인페이지 & 마이페이지</b></summary>
<br>
</details>

<br>

<details>
<summary><b>데둥이 소개</b></summary>
<br>
</details>

<br>

<details>
<summary><b>데둥여지도</b></summary>
<br>
</details>

<br>

<details>
<summary><b>모집 게시판</b></summary>
<br>
</details>

<br>

<details>
<summary><b>맵각코</b></summary>
<br>
</details>

<br>


<br>

## 📜 기술스택

### Spring

- Java 11
- Gradle 7.2
- Spring Boot 2.6.0
- Spring Data JPA, QueryDSL
- Spring Security, jwt
- JUnti5, RestDocs, JACOCO

### Database

- H2
- MySQL

### DevOps

- AWS - EC2 / RDS / S3 / CloudFront
- Github Actions
- Nginx
- Docker, docker-compose
- Slack, Codecov

<br>

## ⚙ 아키텍처

<image width="700" src="https://user-images.githubusercontent.com/71180414/147136566-215adce0-8d2e-4c70-930c-e3580a6a0b63.png"/>

<br>

## 📁 ERD

![dd](https://user-images.githubusercontent.com/71180414/146670649-7f6fce18-d54f-4c70-b3e3-0500a5c3bf2d.png)

<br>
