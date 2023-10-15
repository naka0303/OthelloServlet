- 参考  
  https://joytas.net/programming/jsp_servlet/dicecompgame
  
- ポート解放方法  
  1.lsof -i -P | grep 8080 で、PIDを確認  
  2.kill -9 <PID>で、プロセス削除

- MEMO  
  controller/modelはsrc/main/java下からは絶対動かさない