
import 'dart:convert';

void main() {
  var jsonString = '''
  [
    {"score": 40},
    {"score": 50}
  ]
  ''';

  var scores = jsonDecode(jsonString);
  print(scores is List);

}

Stream<int> countStream(int to) async* {
  for (int i = 1; i <= 10; i++) {
    print('countStream: $i');
    yield i;
  }
}