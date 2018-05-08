//
//  EncryptionEngine.swift
//  PhotoEncryptor
//
//  Created by Marcos Barbieri on 4/26/18.
//  Copyright © 2018 Marcos Barbieri. All rights reserved.
//

import UIKit

public protocol EncryptionEngine {
    
    static func encrypt(message plainText: String, key: String, image coverImage: UIImage) -> UIImage?
    
    static func decrypt(key: String, image coverImage: UIImage) -> String?
    
}
