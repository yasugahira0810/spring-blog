# 20190429

- 再開。VSCode上でJava12にしたらうまく動かなくなったので、Java1.8に戻して進めることにした。
- DBマイグレーションやり直したい時はtarget/db/article.mv.dbを削除してしまえばいいことがわかった。
- [TODO]テスト書く
- [TODO]RestControllerにする（or追加する）
- [TODO]Vue.jsと連携する

# 20190430

- 「JUnit実践入門」のサンプルコードをリポジトリに突っ込んで、VSCode上でのJUnitの動作を確認した。
- その後、「はじめてのSpring Boot」を見ながらSpring Boot用のテストケースを１つ追加した。
- テスト作成時は以下を明確に分け、コメントとして記載しておくと可読性が高まる。ユニットテストで後処理が必要になることはほとんどない。
  + 事前準備    // SetUp
  + 実行       // Exercise
  + 検証       // Verify
  + 後処理     // TearDown