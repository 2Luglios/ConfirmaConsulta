//
//  ViewController.swift
//  ConfirmaConsultaIOS
//
//  Created by Rodrigo Luglio on 12/08/17.
//  Copyright © 2017 2Luglios. All rights reserved.
//

import UIKit
import Firebase

class ViewController: UIViewController {

    override func viewDidLoad() {
        super.viewDidLoad()
        // Do any additional setup after loading the view, typically from a nib.
        let token = Messaging.messaging().fcmToken
        print("FCM token: \(token ?? "")")
        
        Messaging.messaging().subscribe(toTopic: "/topics/teste")
        print("Subscribed to news topic")
        
    }

    override func didReceiveMemoryWarning() {
        super.didReceiveMemoryWarning()
        // Dispose of any resources that can be recreated.
    }


}

