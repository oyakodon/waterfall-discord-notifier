# waterfall-discord-notifier

各サーバへの参加/退出をdiscordに通知するwaterfall (bungeecord) 用のプラグイン

## 機能

※ チャットログは通知しない

- 通知内容
    - Minecraft ユーザ名 (ID)
    - プレイヤーアイコン
    - サーバ名
- 通知タイミング
    - プロキシサーバに接続
    - サーバ間移動
    - プロキシサーバから切断
- 機能を有効化/無効化するコマンドの追加
    - 設定ファイルにも反映

## ビルド

下記いずれかの方法でビルドできます。
生成物は、 `build/libs/*.jar` に生成されます。

- IntelliJ IDEA -> 右上 `Compile jar`
- `./gradlew jar`
