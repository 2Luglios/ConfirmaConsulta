//
//  CadastroViewController.swift
//  ConfirmaConsultaIOS
//
//  Created by Rodrigo Luglio on 24/08/17.
//  Copyright © 2017 2Luglios. All rights reserved.
//

import Foundation
import Firebase

class CadastroViewController: UIViewController {
  
    @IBOutlet weak var scrollView: UIScrollView!
    
    @IBOutlet weak var nomeTextField: UITextField!
    @IBOutlet weak var emailTextField: UITextField!
    @IBOutlet weak var rgTextField: UITextField!
    @IBOutlet weak var cpfTextField: UITextField!
    @IBOutlet weak var senhaTextField: UITextField!
    @IBOutlet weak var repSenhaTextField: UITextField!
    @IBOutlet weak var souMedicoSegmentedControl: UISegmentedControl!
    
    @IBOutlet weak var crmLabel: UILabel!
    @IBOutlet weak var crmTextField: UITextField!
    
    @IBAction func souMedicoChanged(_ sender: Any) {
        
        if souMedicoSegmentedControl.selectedSegmentIndex == 0 {
        
            crmLabel.isHidden = true
            crmTextField.isHidden = true
            
        } else {
        
            crmLabel.isHidden = false
            crmTextField.isHidden = false
            
        }
        
    }
    
    @IBAction func createUser(_ sender: Any) {
        var ehMedico: Bool
        
        if souMedicoSegmentedControl.selectedSegmentIndex == 0 {
            ehMedico = false
        } else {ehMedico = true}
        
        print("createUser")
            Auth.auth().createUser(withEmail: emailTextField.text!, password: senhaTextField.text!) { (user, error) in
            if let user = user {
                // The user's ID, unique to the Firebase project.
                // Do NOT use this value to authenticate with your backend server,
                // if you have one. Use getTokenWithCompletion:completion: instead.
                let uid = user.uid
                let email = user.email
                print("UID: \(uid) - Email: \(email)")
            }
                
        }
        var ref: DatabaseReference!
        
        ref = Database.database().reference()
        
        // Create new post at /user-posts/$userid/$postid and at
        // /posts/$postid simultaneously
        // [START write_fan_out]
        let key = ref.child("usuarios").childByAutoId().key
        
//        let contato = Dictionary<String: Any> [
//        "email": emailTextField,
//        "telefone1": nil,
//        "telefone2": nil
//        ]
//        
//        let usuario = ["nome": nomeTextField.text,
//                       "token": Messaging.messaging().fcmToken,
//                       "e-mail": emailTextField.text,
//                       "rg": rgTextField.text,
//                       "cpf": cpfTextField.text,
//                       "senha": senhaTextField.text,
//                       "ehMedico": ehMedico,
//                       "crm": crmTextField.text] as [String : Any]
//        
//        
//        
        let childUpdates = ["/usuarios/\(key)": usuario]
        
        ref.updateChildValues(childUpdates)
        
        
    }
    
    override func viewDidLoad() {
        
        crmLabel.isHidden = true
        crmTextField.isHidden = true
        
    }
    
    


//    @IBAction func createUser(_ sender: Any) {
//        print("createUser")
//        Auth.auth().createUser(withEmail: emailTextField.text!, password: senhaTextField.text!) { (user, error) in
//            if let user = user {
//                // The user's ID, unique to the Firebase project.
//                // Do NOT use this value to authenticate with your backend server,
//                // if you have one. Use getTokenWithCompletion:completion: instead.
//                let uid = user.uid
//                let email = user.email
//                print("UID: \(uid) - Email: \(email)")
//            }
//            
//        }
//        
//        
//    }


}
