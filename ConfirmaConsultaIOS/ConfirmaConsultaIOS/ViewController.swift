//
//  ViewController.swift
//  ConfirmaConsultaIOS
//
//  Created by Rodrigo Luglio on 12/08/17.
//  Copyright © 2017 2Luglios. All rights reserved.
//

import UIKit
import Firebase
import FirebaseDatabase

class ViewController: UIViewController {

    @IBAction func adicionarUsuario(_ sender: Any) {
        var ref: DatabaseReference!
        
        ref = Database.database().reference()
        
        // Create new post at /user-posts/$userid/$postid and at
        // /posts/$postid simultaneously
        // [START write_fan_out]
        let key = ref.child("usuarios").childByAutoId().key
        let usuario = ["nome": "simuladorIOS",
                    "token": Messaging.messaging().fcmToken]
        
        let childUpdates = ["/usuarios/\(key)": usuario]
        
        ref.updateChildValues(childUpdates)
        // [END write_fan_out]
        
    }
    @IBAction func lerDados(_ sender: Any) {
        
        var ref: DatabaseReference!
        
        ref = Database.database().reference()
        
        ref.child("mensagens").observeSingleEvent(of: .value, with: { (snapshot) in
            // Get user value
            let value = snapshot.value as? NSDictionary
            for child in snapshot.children {
                let filho = child as! DataSnapshot
                let conteudo = filho.value as! NSDictionary
                print(conteudo["mensagem"])
            }
            //print(snapshot)
        }) { (error) in
            print(error.localizedDescription)
        }
        
        
    }
    @IBAction func enviaMsg(_ sender: Any) {
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
            
            print(data)
        }
        
        task.resume()
    }
    override func viewDidLoad() {
        super.viewDidLoad()
        // Do any additional setup after loading the view, typically from a nib.
        let token = Messaging.messaging().fcmToken
        print("FCM token: \(token ?? "")")
        
        Messaging.messaging().subscribe(toTopic: "/topic/teste")
        
    }

    override func didReceiveMemoryWarning() {
        super.didReceiveMemoryWarning()
        // Dispose of any resources that can be recreated.
    }


    
}

