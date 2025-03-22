# 간단한 계산기 화면 제작.
## .java로 가서 public void plus(View e) { String a = firstNumber.getText().toString(); String b = secondNumber.getText().toString(); int result = Integer.parseInt(a) + Integer.parseInt(b); value.setText("" + result); } <- -는 int result = Integer.parseInt(a) - Integer.parseInt(b);와 같이 변경해준다.(*, /도 동일한 방법)         value.setText("" + result);을 처음 작성을 할 때 ("" + result);을 이해하지 못해 ("" - result);, ("" * result);, ("" / result);로 작성하는 실수를 했다. 조심하도록 하자!
## ![스크린샷 2025-03-23 001848](https://github.com/user-attachments/assets/dce2ab8f-9726-41f1-8b08-772b53d72298) ![스크린샷 2025-03-23 001855](https://github.com/user-attachments/assets/e5c5b5d8-d9b0-4a5f-b431-e4060b6bbf59) ![스크린샷 2025-03-23 001903](https://github.com/user-attachments/assets/17e00d00-817b-449c-8339-60fb43345bf2) ![스크린샷 2025-03-23 001910](https://github.com/user-attachments/assets/f847405c-a7e1-4209-a5d4-94f64c48adc7)



