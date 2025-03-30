# 프레임 레이아웃을 사용하여 텍스트 뷰 3개를 중첩하여 배치한 후에 버튼을 눌러서 원하는 뷰가 보이게 하는 앱 작성.
## 뷰 속성 중 visibility 속성 이용. visibility = "visible"은 뷰가 보이는 상태. visibility = "visible"은 뷰가 보이지 않는 상태.

### 책과 ppt의 예제대로 작성을 하면 ![스크린샷 2025-03-30 182737](https://github.com/user-attachments/assets/1541b5cc-dd5e-4860-b2f6-967122b1d0c7) 자꾸 에러가 발생된다. 6시간동안 AI와 지인의 도움을 받으려 했지만 실패했다.
### 해결 방법은 의외로 간단했다. ![스크린샷 2025-03-30 182625](https://github.com/user-attachments/assets/bf7180d9-127c-4be0-8288-1275a9a70bd8) android.nonFinalResIds=false을 추가해주면 해결된다...(6시간동안 나는 무엇을 했을까...)
### ![스크린샷 2025-03-30 182544](https://github.com/user-attachments/assets/8669932f-f060-4409-82f7-3aa6e24a62a1) ![스크린샷 2025-03-30 182552](https://github.com/user-attachments/assets/82970f3a-a4bd-4420-b4b3-29aec6546ed8) ![스크린샷 2025-03-30 182559](https://github.com/user-attachments/assets/1e1202f0-829b-4c6d-b62d-3561fb6e5fd6) ![스크린샷 2025-03-30 182606](https://github.com/user-attachments/assets/f0e0ec7c-81fe-43cb-888f-b8248a569d12)
