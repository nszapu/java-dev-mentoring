## Task 4
| Index                   | B-tree     | Hash       | GIN        | GIST       |
| ----------------------- | ----------:| ----------:| ----------:| ----------:|
| create time before data | 73 ms      | 34 ms      | 62 ms      | 89 ms      |
| create time after data  | 659 ms     | 651 ms     | 7456 ms    | 38614 ms   |
| query a                 | 2.070 ms   | 1.894 ms   | 3.220 ms   | 4.361 ms   |
| query b                 | 10.123 ms  | 10.106 ms  | 10.691 ms  | 18.596 ms  |
| query c                 | 13.412 ms  | 12.437 ms  | 0.744 ms   | 9.446 ms   |
| query d                 | 141.265 ms | 123.477 ms | 386.552 ms | 412.504 ms |