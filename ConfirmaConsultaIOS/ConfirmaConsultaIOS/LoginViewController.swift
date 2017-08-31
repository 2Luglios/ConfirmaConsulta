//
//  LoginViewController.swift
//  ConfirmaConsultaIOS
//
//  Created by Rodrigo Luglio on 24/08/17.
//  Copyright © 2017 2Luglios. All rights reserved.
//

import Foundation
import Firebase
import FirebaseStorage



class LoginViewController: UIViewController {

    var handle: AuthStateDidChangeListenerHandle?
    
    @IBOutlet weak var emailTextField: UITextField!
    @IBOutlet weak var senhaTextField: UITextField!
    
    @IBAction func signIn(_ sender: Any) {
        print("signin")
        
        Auth.auth().signIn(withEmail: emailTextField.text!, password: senhaTextField.text!) { (user, error) in

            if let user = user {
                // The user's ID, unique to the Firebase project.
                // Do NOT use this value to authenticate with your backend server,
                // if you have one. Use getTokenWithCompletion:completion: instead.
                let uid = user.uid
                let email = user.email
                print("UID: \(uid) - Email: \(email)")
            }

            
        }


    }
    @IBAction func logout(_ sender: Any) {
        
        do {
            try Auth.auth().signOut()
        } catch let error as NSError {
            print(error)
        }
        
        
    }

    @IBAction func lerStorage(_ sender: Any) {
        
        let storage = Storage.storage(url: "gs://confirmaconsulta-63f26.appspot.com")
        let ref = storage.reference()
        let image = ref.child("images/ettore-luglio-120.1394081564.jpg")
        image.getData(maxSize: 1 * 1024 * 1024) { data, error in
            if let error = error {
                // Uh-oh, an error occurred!
            } else {
                // Data for "images/island.jpg" is returned
                print(data?.count)
            }
        }
        
        
    }
    @IBAction func enviaDado(_ sender: Any) {
        
        let storage = Storage.storage(url: "gs://confirmaconsulta-63f26.appspot.com")
        let ref = storage.reference()
        let dado = Data()
        let dadoRef = ref.child("images/lalala.txt")
        let uploadTask = dadoRef.putData(dado, metadata: nil) { (metadata, error) in
            guard let metadata = metadata else {
                // Uh-oh, an error occurred!
                return
            }
            // Metadata contains file metadata such as size, content-type, and download URL.
            let downloadURL = metadata.downloadURL
        }
        
    }
    override func viewWillAppear(_ animated: Bool) {
        print("aparecendo")
        handle = Auth.auth().addStateDidChangeListener { (auth, user) in
            if let user = user {
                // The user's ID, unique to the Firebase project.
                // Do NOT use this value to authenticate with your backend server,
                // if you have one. Use getTokenWithCompletion:completion: instead.
                let uid = user.uid
                let email = user.email
                print("UID: \(uid) - Email: \(email)")
            }

        }
        


    }
    

    
    override func viewWillDisappear(_ animated: Bool) {
        Auth.auth().removeStateDidChangeListener(handle!)
    }



}
