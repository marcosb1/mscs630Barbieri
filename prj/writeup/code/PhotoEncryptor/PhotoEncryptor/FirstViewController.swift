//
//  FirstViewController.swift
//  PhotoEncryptor
//
//  Created by Marcos Barbieri on 4/26/18.
//  Copyright © 2018 Marcos Barbieri. All rights reserved.
//

import UIKit

class FirstViewController: UIViewController, UITextFieldDelegate, UIImagePickerControllerDelegate, UINavigationControllerDelegate {
    
    //MARK: Properties
    
    @IBOutlet weak var imageNameLabel: UILabel!
    @IBOutlet weak var imageNameTextField: UITextField!
    @IBOutlet weak var imageView: UIImageView!
    @IBOutlet weak var keyTextField: UITextField!
    
    //MARK: Controller Functions
    
    override func viewDidLoad() {
        super.viewDidLoad()
        // Do any additional setup after loading the view, typically from a nib.
    }

    override func didReceiveMemoryWarning() {
        super.didReceiveMemoryWarning()
        // Dispose of any resources that can be recreated.
    }
    
    func imagePickerControllerDidCancel(_ picker: UIImagePickerController) {
        // Dismiss the picker if the user canceled.
        dismiss(animated: true, completion: nil)
    }
    
    func imagePickerController(_ picker: UIImagePickerController, didFinishPickingMediaWithInfo info: [String : Any]) {
        // The info dictionary may contain multiple representations of the image. You want to use the original.
        guard let selectedImage = info[UIImagePickerControllerOriginalImage] as? UIImage else {
            fatalError("Expected a dictionary containing an image, but was provided the following: \(info)")
        }
        
        // Set photoImageView to display the selected image.
        imageView.image = selectedImage
        
        // Dismiss the picker.
        dismiss(animated: true, completion: nil)
    }
    
    //MARK: UITextFieldDelegate
    
    func textFieldShouldReturn(_ textField: UITextField) -> Bool {
        // Hide the keyboard.
        textField.resignFirstResponder()
        return true
    }
    
    //MARK: Actions
    
    @IBAction func selectImageFromPhotoLibrary(_ sender: UITapGestureRecognizer) {
        // Hide the keyboard.
        imageNameTextField.resignFirstResponder()
        
        // UIImagePickerController is a view controller that lets a user pick media from their photo library.
        let imagePickerController = UIImagePickerController()
        
        // Only allow photos to be picked, not taken.
        imagePickerController.sourceType = .photoLibrary
        
        // Make sure ViewController is notified when the user picks an image.
        imagePickerController.delegate = self
        
        present(imagePickerController, animated: true, completion: nil)
    }
    
    @IBAction func encryptImage(_ sender: UIButton) {
        
        guard let image = imageView.image else {
            return
        }
        
        guard let plainText = imageNameTextField.text?.lowercased().replacingOccurrences(of: " ", with: "") else {
            throwErrorAlert(title: "Error", message: "Please provide a valid plaintext.")
            return
        }
        
        guard let key = keyTextField.text?.lowercased() else {
            throwErrorAlert(title: "Error", message: "Please provide a valid key.")
            return
        }
        
        print("Count Plain: \(plainText.count) Count Key: \(key.count)")
        print("\(key.contains(" "))")
        if plainText.count != key.count || plainText.count < 1 || key.count < 1 || key.contains(" ") {
            throwErrorAlert(title: "Error", message: "Please provide a valid plaintext and key.")
            return
        } else {
            imageView.image = ChannelSwitchEncryptionEngine.encrypt(message: plainText,
                                                                    key: key,
                                                                    image: image)
            
            guard let encryptedImage = imageView.image else {
                return
            }
            
            // YOU NEED TO USE PNG, OTHERWISE THE COMPRESSION WILL BE LOSSY
            // AND THE DATA WILL BE GONE
            let imgData = UIImagePNGRepresentation(encryptedImage)
            let pngImage = UIImage(data: imgData!)
            UIImageWriteToSavedPhotosAlbum(pngImage!, nil, nil, nil)
            
            // notify user of action
            let alert = UIAlertController(title: "Success", message: "Photo Successfully saved to camera roll.", preferredStyle: .alert)
            alert.addAction(UIAlertAction(title: "Ok", style: .default, handler: nil))
            self.present(alert, animated: true)
            
            // cleanup
            clearViews()
        }
    }
    
    func throwErrorAlert(title: String, message: String) {
        let alert = UIAlertController(title: title, message: message, preferredStyle: .alert)
        alert.addAction(UIAlertAction(title: "Ok", style: .default, handler: nil))
        self.present(alert, animated: true)
    }
    
    func clearViews() {
        imageNameTextField.text = nil
        keyTextField.text = nil
        imageView.image = UIImage(named: "defaultPhoto")
    }
}

