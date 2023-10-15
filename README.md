# OthelloServlet

## 参考  
  https://joytas.net/programming/jsp_servlet/dicecompgame
  
## ポート解放方法  
  1.lsof -i -P | grep 8080 で、PIDを確認  
  2.kill -9 <PID>で、プロセス削除

## MEMO  
  controller/modelはsrc/main/java下からは絶対動かさない
  
## その他
- markdown記法参考  
https://qiita.com/tbpgr/items/989c6badefff69377da7