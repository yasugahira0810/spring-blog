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

## 3.3節相当の実装

- RESTアプリ（@RestControllerベース）からWeb画面アプリ（@Controllerベース）への書き換えにあたり、メソッドは以下のように変わる。

```Java
// @RestController
@GetMapping
List<Article> getArticles(Article article) {
    List<Article> articles = articleService.findAll();
    return articles;
}

// @Controller
@GetMapping
String list(Model model) {
    List<Article> articles = articleService.findAll();
    model.addAttribute("articles", articles);
    return "articles/list";
}
```

- RESTアプリだとArticleのリストをそのまま戻していたが、WebアプリだとArticleのリストはmodelに突っ込んで、戻り値は画面名（String）になる。
- modelは処理結果を格納しておくもの。

### 3.3.1

- 表示はリンクでもいい気がしたけど、他と足並み揃えた。削除はpostじゃなくてgetでもできるはずなので、getにした。

# 20180508

### 3.3.2

- コントローラの中の@ModelAttributeも、@RequestMappingあってのアノテーション。  
  @GetMappingなどのついたメソッドの前に実行され、結果は自動でModelに格納される。  
  model.addAttribute(new CustomerForm());という感じで、()内は@ModelAttributeの戻り値。
- 投稿日時の取得で試行錯誤した。以下のような方法を考えた。
  1. 投稿日時をHTMLの何かしらの機能で取得してhiddenで送付
  2. 投稿日時をjavascriptで取得してhiddenで送付
  3. 投稿日時をthymeleaf(Java)で取得してhiddenで送付
  4. サーバ側にリクエストが送られてきてから、Java側で設定
- ググるとjavascriptやPHPでやるサンプルが出てきたので、HTML自体には該当の機能はないと判断して1は切った。
- 2と3だったら3がいいと思った。いろいろな機能使いたくないので。3のやり方は[こちら](http://javatechnology.net/spring/thymeleaf-java-logic/)が非常に参考になった。
- 3と4どちらにしようと思ったが、ArticleControllerはFormから受け取った値をドメインオブジェクト(article)にコピーしているだけなので、ここでFormやドメインオブジェクトはいじりたくないと思った。
- あるべき姿を考えても、3と4だったらボタンが押された3の方がいい気がしたので、3で実装することにした。
- 最初Article.javaにgetPostDate()を実装したが、そうするとlist.htmlに表示される記事の投稿日時が画面描画した時刻になってしまって、変だった。
- その後getPostDate()を実装するのはArticle.javaではなくArticleForm.javaだと気づき、そちらに実装したところ、うまく動くようになった。

### 3.3.3

- @GetMapping(path = "edit", params = "form")のformは、/article/edit?form&id={id}のformと紐付いたものと気づいた。
- goToTopメソッド用意したが、ここにもバリデーションが効いてしまっていて、タイトルなどが空の状態だとバリデーションエラーで戻れなくなってしまうことに気づいた。
- BootStrapのバージョンが、書籍が3.3.7のところ、Mavenリポジトリの最新は4.1.0になっていたので、アップデート。  
  invalid LOC headerというエラーが出てアプリが動かなくなったので、一度.m2を削除してmaven install仕直し。  
  Bootstrapのバージョンアップで手間取ったが、実際はCDNの参照先を変えれば良かっただけなのでは？という気がしてきた。
- 先に書いたgetPostDate()のやつ、実はうまく言っていなかった。getPostDate()はLombookか何かが自動で設定している名前のようで、うまくいっているように見えていただけだった。結局4の実装をしてしまった。
- @PostMapping(path = "edit", params = "goToTop")のところ、書籍だと@RequestMapping, 訂正表だと@PostMapping, GitHubのコードだと@GetMappingで、どれが正しいんだ？と思ったが、これは@PostMappingが正しい。なぜならgoToTopは戻るボタン、つまりform、つまりPOSTメソッドが使われているから。
  + paramsという名前から、GETメソッドのパラメータを表しているのかと思ったが、そうではないらしい。このparamsはhtmlの<input class="btn btn-secondary" type="submit" name="goToTop" value="戻る"/>のnameと紐付いているらしい。
- 更新処理のところでハマって、結果的に元よりいい感じにした。
  + 元はcustomerをnewして、copyPropertiesで、更新したlastNameとfirstNameを  
    customerに渡す。でもこれだとidが入っていないから、idをセットする。
  + このやり方を真似たところpostDateの設定がうまくいかなかった。postDateは  
    @RequestParamで渡していない、かつ更新対象でもないのでformにもないため、  
    DBに反映するタイミングでnullがセットされてしまい、エラーになっていた。  
    そこで、articleをnewするのではなく、findOneで更新対象のデータを事前に  
    とってきて、それを更新したデータで上書きする形にした。
  + 元よりいい方法だと思ったけど、editForの方でfindOne(id)していて、二度手間だな。。。まぁそのまま進む。


```java
@PostMapping(path = "edit")
    String edit(@RequestParam Integer id, @Validated CustomerForm form, BindingResult result) {
        if (result.hasErrors()) {
            return editForm(id, form);
        }
        Customer customer = new Customer();
        BeanUtils.copyProperties(form, customer);
        customer.setId(id);
        customerService.update(customer);
        return "redirect:/customers";
    }

@PostMapping(path = "edit")
    String edit(@RequestParam Integer id, @Validated ArticleForm form, BindingResult result) {
        if (result.hasErrors()) {
            return editForm(id, form);
        }
        Article article = articleService.findOne(id);
        BeanUtils.copyProperties(form, article);
        articleService.update(article);
        return "redirect:/articles";
    }
```

# 20180509

## 3.4

- 当初「set baselineOnMigrate to true to initialize the schema history table.」というエラーが出ていたので、ググって、application.propertiesに「spring.flyway.baseline-on-migrate=true」て書いたらエラー出なくなった。その後はこれコメントアウトしてもエラーは出なくなった。

## 3.5

- 書籍通りの内容は特に問題なく実装できた。ただブログ用途としてはおかしな状態なので、この後変更していく。

## 認証・認可改善

- SecurityConfigに「.antMatchers("/loginForm", "/articles", "/articles/list", "/articles/show").permitAll()」て書くことで、認証前でもブログが見える状態にはした。
- ユーザが一人であれば一応使える状態。（複数人だと、user1がuser2の記事を消せてしまう。）
- ただ存在しないURLやlocalhost:8080/のリダイレクト先がloginFormになってしまっていて、この点を修正したいがそこでハマっている。
  + 具体的には、デフォルトのリダイレクト先は/articles/listにして、ログインしたい人は/articles/listのボタンからログインフォームに遷移する形にしたい。
  + 存在しないURLやlocalhost:8080/のリダイレクト先がloginFormになるのはSpring Securityのデフォルトの動作で、これを変更する必要がある。設定一発でいけどうな気がしたが、どうもそうではないらしい。
  + 参考になりそうなページはこのあたり[1](https://ishiis.net/2016/08/27/spring-security-custom-authentication/), [2](http://d.hatena.ne.jp/ocs/?of=17)。AuthenticationEntryPointとかbuildRedirectUrlToLoginPageとかをちゃんと調べる必要がある。
- とりあえずログイン画面に/article/listへのボタンをつけた。
- 現状だとユーザがログインしているかしていないかがわからないので、次はそこをわかるようにしたい。ログインしてたらボタンを表示する、みたいな処理を入れたい。
