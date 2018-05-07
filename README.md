# Spring Blog

## 処理一覧

|処理名|HTTPメソッド|リソース・パス|画面名|
|-|-|-|-|
|タイトル一覧表示|GET|/articles|articles/list|
|記事表示|GET|/articles?id={id}|articles/id|
|記事作成|POST|/articles/create|記事表示へリダイレクト|
|記事編集フォーム表示|GET|/articles/edit?form&id={id}|articles/edit|
|記事編集|POST|/articles/edit&id={id}|記事表示へのリダイレクト|
|記事削除|POST|/articles/delete&id={id}|タイトル一覧表示へのリダイレクト|
