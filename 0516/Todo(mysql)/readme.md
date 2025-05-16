# Android Java To-Do App with MySQL

안드로이드 스튜디오에서 Java로 개발한 To-Do 앱입니다.  
MySQL 데이터베이스와 직접 JDBC로 연결하여 할 일 목록을 저장하고 불러옵니다.  

---

## 주요 기능

- 할 일(Task) 추가 및 조회
- 날짜와 함께 할 일 입력
- MySQL 데이터베이스 연동 (JDBC 사용)
- 심플하고 깔끔한 UI 디자인
- Floating Action Button으로 쉽게 할 일 추가

---

## 프로젝트 구조

MyTodoApp/
├── app/
│ ├── java/com/example/mytodoapp/
│ │ ├── MainActivity.java
│ │ ├── AddTaskActivity.java
│ │ ├── DBHelper.java
│ │ ├── Task.java
│ │ └── TaskAdapter.java
│ └── res/layout/
│ ├── activity_main.xml
│ ├── activity_add_task.xml
│ └── item_task.xml

---

## 설치 및 실행 방법

1. MySQL 서버 준비 및 데이터베이스 생성

```sql
CREATE DATABASE mytodo;
USE mytodo;
CREATE TABLE tasks (
    id INT AUTO_INCREMENT PRIMARY KEY,
    title VARCHAR(255) NOT NULL,
    date VARCHAR(50) NOT NULL
);
