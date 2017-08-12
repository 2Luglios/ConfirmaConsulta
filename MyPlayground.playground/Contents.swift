import PlaygroundSupport
import Foundation

let url = URL(string: "https://us-central1-confirmaconsulta-63f26.cloudfunctions.net/sendMSG?origem=dNXRTYiFZBA:APA91bEtlWsQcGQhRwAQTzCvUBrKelzxCEv9H9v4AVR33vg3VVNhDzl7COdWTPgqZA2EDLmYiSxovvhh5ss4ka3DwjNSaHgu3cJbBk6I2DFUjSYHYY34jBkj-tUiDnCls0NoypzZv6-8%20dNXRTYiFZBA:APA91bEtlWsQcGQhRwAQTzCvUBrKelzxCEv9H9v4AVR33vg3VVNhDzl7COdWTPgqZA2EDLmYiSxovvhh5ss4ka3DwjNSaHgu3cJbBk6I2DFUjSYHYY34jBkj-tUiDnCls0NoypzZv6-8&destino=foLPh5EATlg:APA91bGAHwLSmNOrzlyG7BUQ1xIU9jK1vquCgm5q1VkAknz7zmcJhXdrwlo_Xjy87XPzIzLlJFjz2n2djSlCsNO2HDgBxgcCfnexVxA-8rdDUEzU7BUhAK1mtrkErWAYkSH1QqqkQ6uk&mensagem=lolololo")

let task = URLSession.shared.dataTask(with: url!) { data, response, error in
    guard error == nil else {
        print(error!)
        return
    }
    guard let data = data else {
        print("Data is empty")
        return
    }
    
    let json = try! JSONSerialization.jsonObject(with: data, options: [])
    print(json)
}

task.resume()
PlaygroundPage.current.needsIndefiniteExecution = true
