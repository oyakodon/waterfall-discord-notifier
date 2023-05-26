# waterfall-discord-notifier

各サーバへの参加/退出をdiscordに通知するwaterfall (bungeecord) 用のプラグイン

![スクリーンショット_Discord通知](docs/notice_joined.png)

## 機能

- 通知内容
    - Minecraft ユーザ名 (ID)
    - プレイヤーアイコン
    - サーバ名
    - 参加プレイヤー数
- 通知タイミング
    - プロキシサーバに接続
    - サーバ間移動
    - プロキシサーバから切断
- コマンド
    - `/wfdiscordnotifier reload`: 設定ファイルをリロード
    - `/wfdiscordnotifier toggle`: 通知機能の有効/無効を切り替え
    - (コマンド短縮形: `/nt`)

## ビルド

下記いずれかの方法でビルドできます。
生成物は、 `build/libs/*.jar` に生成されます。

- IntelliJ IDEA -> 右上 `Compile jar`
- `./gradlew jar`
