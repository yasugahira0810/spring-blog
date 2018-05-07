# 20180507

- 最初にドメイン・オブジェクトとしてArticleクラスを作成する。元：P.43。
- 次にリポジトリ・クラス、そしてサービス・クラス。元：P.43-45。
- アプリ作り始める流れとしては以下のイメージと認識した。
  + 最初にテーブルの項目考える。
  + Javaの世界でのテーブルに当たるドメイン・クラスを実装する。
  + ドメイン・クラスのCRUD処理を、リポジトリ・クラスに実装する。
    - リポジトリ・クラスは永続化技術を隠蔽したり、データアクセス処理を共通化するためのもの。小さいアプリならリポジトリ・クラスはなしで、サービス・クラスからドメイン・クラスを呼び出すのもあり。のハズ。
    - 実際、現状のArticleServiceクラスのsaveメソッドは、ArticleRepositoryのsaveメソッド呼び出しているだけ。
  + リポジトリ・クラスを活用したビジネスロジックをサービス・クラスに実装する。
- 日付処理周りは、Java8から入ったDate and Time APIを使う。
- とりあえずP.45まで動作確認含めてやった。
- エントリポイントのSpringBlogApplication.javaのデフォルトは以下を参照。  
  現状はCLIベースなので、CommandLineRunnerを継承して、SpringApplication.runを実行している。  
  Webアプリにする時にはこの辺りは消すはず。

```Java
package com.spring.blog;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class SpringBlogApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringBlogApplication.class, args);
	}
}
```
