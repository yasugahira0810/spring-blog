# Spring Blog

## 処理一覧

|処理名|HTTPメソッド|リソース・パス|画面名|
|-|-|-|-|
|ログインフォーム表示|GET|loginForm|loginForm|
|タイトル一覧表示|GET|/articles|articles/list|
|記事作成フォーム表示|GET|/articles/create|articles/create|
|記事作成|POST|/articles/create|タイトル一覧表示へリダイレクト|
|記事表示|GET|/articles/show?form=表示&id={id}|articles/show|
|記事編集フォーム表示|GET|/articles/edit?form=編集&id={id}|articles/edit|
|記事編集|POST|/articles/edit&id={id}|タイトル一覧表示へのリダイレクト|
|記事削除|GET|/articles/delete&id={id}|タイトル一覧表示へのリダイレクト|
