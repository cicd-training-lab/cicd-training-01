# cicd-training — 「GitHubによるCI/CD入門」演習プロジェクト

在庫管理のドメインロジック (在庫引当・発注判定) を題材にした Java ライブラリです。
この研修では、このプロジェクトのテストを GitHub Actions で自動実行する CI/CD を組み立てます。

## プロジェクト構成

```text
cicd-training-<受講者 ID>/
├── pom.xml                     Maven 設定 (Java 21 + JUnit 5)
├── mvnw / mvnw.cmd             Maven Wrapper (Maven のインストールは不要)
├── .gitattributes              改行コードの設定 (変更しない)
├── src/main/java/              テスト対象のコード (在庫引当・発注判定)
├── src/test/java/              JUnit 5 のテストコード (18 ケース)
└── docs/workflow-hints/        演習で使う Workflow の YAML の見本 (コピー用)
```

## ローカルでのテスト実行 (第 2・3 章の演習で使います)

Git Bash、macOS、Linux の場合:

```bash
./mvnw test
```

PowerShell の場合:

```powershell
.\mvnw.cmd test
```

- Java 21 が必要です (`java -version` で確認)
- Maven のインストールは不要です (Maven Wrapper `./mvnw` を同梱)
- 全 18 ケースが成功すると `BUILD SUCCESS` と表示されます

## Workflow の YAML をコピーする (`docs/workflow-hints/`)

`docs/workflow-hints/` には、演習で使う Workflow の YAML の見本が入っています。教材に YAML を載せたものは、教材の YAML と 1 文字も違わない同じ内容です (`ch5-delivery-pages.yml` は除きます。演習 5-2 で使うファイルは講師が案内します)。
教材の YAML を手で入力する代わりに、リポジトリ直下で次のようにコピーできます。教材と同じ内容のファイルなら、どちらを使っても結果は同じです。

```bash
cp docs/workflow-hints/ch2-hello.yml .github/workflows/hello.yml
```

| ファイル | 教材の箇所 (このファイルをコピーする演習と手順) | コピー先 |
| --- | --- | --- |
| `ch2-hello.yml` | 第 2 章 演習 2-1 の手順 5 (push で文字列を出力する最初の Workflow) | `.github/workflows/hello.yml` |
| `ci.yml` | 第 2 章 演習 2-2 の手順 5 (PR で Java のテストを実行する CI) | `.github/workflows/ci.yml` (置き換え) |
| `ch5-delivery-jar.yml` | 第 5 章 演習 5-1 の手順 4 (`main` への push で JAR を作り、Artifact に保存する) | `.github/workflows/delivery.yml` |
| `ch5-delivery-pages.yml` | 第 5 章 演習 5-2 の手順 3 の「Pages のとき」(テストレポートを Pages へ公開する。使うファイルは講師が案内します) | `.github/workflows/delivery.yml` (置き換え) |
| `ch5-delivery-fallback.yml` | 第 5 章 演習 5-2 の手順 3 の「fallback のとき」(Pages を使えないとき。JAR と HTML のレポートを Artifact に保存する) | `.github/workflows/delivery.yml` (置き換え) |
| `ch5-secret.yml` | 第 5 章 演習 5-3 の手順 2 (講師が登録した Secret を参照する) | `.github/workflows/secret-check.yml` |

- コピーは、教材の手順でそのファイルを使う時点で行います。後の章のファイルを先にコピーしないでください
- コピー先に同じ名前のファイルがあると上書きされます。第 2 章 演習 2-2 の `ci.yml` は、手順 4 で `hello.yml` を `git mv` で `ci.yml` に名前を変えてから、手順 5 で上書きします
- コピー先のフォルダー `.github/workflows/` がまだない場合は、先に作ります (第 2 章 演習 2-1 の手順 4)
- PowerShell でも同じ `cp` の行を使えます (`cp` は `Copy-Item` の別名です)
- Action は完全なコミット SHA で固定してあります。SHA や行末の版のコメントは書き換えないでください

<!-- 保守の注意: docs/workflow-hints/ のファイルを変えるときは、教材の YAML も同時に直す。 -->

## 注意

- `.github/workflows/` は最初は存在しません。第 2 章の演習 2-1 で自分で作成します
- `exercise/bug-level1` 〜 `bug-level3` ブランチは第 3 章「CI 失敗を調査・修正する」の演習用です。指示があるまで触らないでください
- `.gitattributes` は、Windows で clone しても `./mvnw` が動くように改行コードを固定する設定です。変更・削除しないでください
