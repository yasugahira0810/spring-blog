# 20180507

## 2.1節相当の実装

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
  現状はCLIベースなので、CommandLineRunnerを実装して、SpringApplication.runを実行している。  
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

## 2.2節相当の実装

- Article.java(ドメイン・クラス)はJPA対応にした途端にアノテーションが付きまくった。
- ArticleRepository.java(リポジトリ・クラス)は、@Repositoryアノテーションのついたクラスから、JpaRepositoryクラスを継承したインタフェースに変わった。
- SpringBlogApplicationで、@AutowiredしているのがArticleServiceからArticleReposirotyに変わっている点に注意。あくまで説明の都合で、深い理由はないと認識している。
- application.propertiesの設定しなくても、エラーは出ないで実行できた。ドメイン・オブジェクトとDBが紐づくのは、schema.sqlとかを配置して初めてなのかな？
  + 空のschema.sqlを配置したらエラーが吐かれた。やっぱりそういうことらしい。
  + P.68にこの時点ではdata.sqlとschema.sqlは作るなって書いてあった。
- 2.3.2, 2.3.3あたりでだいぶはまった。schema.sql, data.sqlは実行されているが、実行後にテーブルがDROPされてしまい、結果としてP.70の出力に、SpringBlogApplicationで追加したデータしか表示されない事態になっていた。
- 原因はJPA(Hibernate)の初期化処理っぽかったので、application.propertiesにspring.jpa.hibernate.ddl-auto=noneの設定を入れたところ、事象が解消した。
- 逆にschema.sql, data.sqlが実行されるのを抑止したい時は、spring.datasource.initialization-mode=neverで設定する。
  + schema.sqlは、SQL文の中にIF NOT EXISTSを書けば、そちらで制御することも可能。
- 2.3.4のページング処理は、機能の紹介のみでWebの画面には反映されないので、読むだけでアプリには反映しない。

### TODO

- findAllOrderByPostDateで時刻順でソートされることを確認するテストを作成する

## 3.1節相当の実装

- サービス・クラスのsaveメソッドはcreateメソッドとupdateメソッドに別れたが、やっていることは同じ。
- コントローラは、クラスに@RequestMapping("api/customers")みたいなパス付きのアノテーションを書いた上で、メソッドには@GetMapping, @PutMapping, @PostMapping, @DeleteMappingを使う、@GetMappingのパスは@RequestMappingからの相対パスになる、という感じのものと理解した。

## 3.2節相当の実装（と言いつつ読んだだけ）

- P.85, 86のノートの内容は、後で日記のURIをidベースからタイムスタンプベースに変更する時に参考になりそうな内容。
- P.87の内容は、画面のページネーション実装する際に、ベースになりそうな内容。
