# 5개 이상의 엑티비티로 누군가에게 도움이 되는 앱 만들어보기!

### ![스크린샷 2025-04-12 151944](https://github.com/user-attachments/assets/1d31aa91-1c20-4fb8-b96a-061d307663fb)
### 3초뒤에 로그인 화면으로 넘어간다. 
### ![스크린샷 2025-04-12 151950](https://github.com/user-attachments/assets/fd16116f-9b47-4cb3-8c44-e7f4f05e24ec)
### ![스크린샷 2025-04-12 152000](https://github.com/user-attachments/assets/420a3404-0d67-4e40-84c3-a608a77d3d6f)![스크린샷 2025-04-12 152008](https://github.com/user-attachments/assets/7f5c2ed4-c29a-4e0d-84e4-dc70cc527127)
### 로그인 정보가 잘못되면 로그인이 실패한다.
### ![스크린샷 2025-04-12 152022](https://github.com/user-attachments/assets/04432afe-fbd7-414a-ae1c-eb9cefa5dee4)![스크린샷 2025-04-12 152029](https://github.com/user-attachments/assets/98b980c7-8b4e-469e-a3c6-81a7ef64a2f2)
### 로그인이 완료되면 2초뒤에 다음 화면으로 전환된다. 
#####  private boolean isUserValid(String username, String password) {
#####     // 실제로는 여기에서 서버 또는 로컬 데이터베이스를 통해 인증을 확인해야 한다.
#####     // 이 예제에서는 더미 데이터를 사용하여 단순하게 인증 성공 여부를 판단한다.
#####     return username.equals("san") && password.equals("1111");
#####     }
### ![스크린샷 2025-04-12 152036](https://github.com/user-attachments/assets/556a0545-00df-4005-8dbe-a3d146637b15)
### ![스크린샷 2025-04-12 152053](https://github.com/user-attachments/assets/a40b6212-ecc2-4ec3-bdbb-21d1750a09f5)![스크린샷 2025-04-12 152112](https://github.com/user-attachments/assets/1d28968e-2928-42f3-8bdd-7b8d9bc9f98f)
### 공식사이트와 인스타그램 버튼
### ![스크린샷 2025-04-12 152209](https://github.com/user-attachments/assets/5938d6e6-6591-422f-9024-b1868a73fa93)![스크린샷 2025-04-12 152224](https://github.com/user-attachments/assets/457ff95e-aa5c-423f-897f-391b30913155)
### 지도보기와 전화걸기 버튼
### ![스크린샷 2025-04-12 152239](https://github.com/user-attachments/assets/edfcba6a-3781-47fb-8004-e8440fe3248e)

### 설문조사 버튼
### ![스크린샷 2025-04-12 152253](https://github.com/user-attachments/assets/a778b9f9-46be-4d5d-a2c4-ac39867adbd8)
### 思い出버튼(이것은 사적이지만 나와 동료들의 추억을 위한 버튼이다. 모든 사진을 담고싶지만 일부만 담아보았다. 개인적인 사진이므로 첫번째 사진만 readme에서 보이도록 하겠다.)
### 일본에 거주했을 때 일했던 회사와 호텔, 호텔 이용자들에게 도움이 되는 앱을 만들어보았다. 최대한 책의 내용을 참고하여 작성하였고 아주 조금의 AI의 도움을 받아 제작하였다.
#### MainActivity는 교재p212 예제-스플래시 화면을 참고
#### MainActivity2와 MainActivity3은 교재p216 예제-로그인 액티비티와 서브액티비티 예제를 참고
#### MainActivity4는 교재p224 예제-전화, 지도 보기를 참고
#### MainActivity5는 교재p115 예제-이미지 뷰어 만들기와 AI참고
