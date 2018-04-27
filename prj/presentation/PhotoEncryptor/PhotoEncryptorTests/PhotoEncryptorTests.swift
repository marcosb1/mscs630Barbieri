//
//  PhotoEncryptorTests.swift
//  PhotoEncryptorTests
//
//  Created by Marcos Barbieri on 4/26/18.
//  Copyright © 2018 Marcos Barbieri. All rights reserved.
//

import XCTest
import UIKit
@testable import PhotoEncryptor

class PhotoEncryptorTests: XCTestCase {
    
    override func setUp() {
        super.setUp()
        // Put setup code here. This method is called before the invocation of each test method in the class.
    }
    
    override func tearDown() {
        // Put teardown code here. This method is called after the invocation of each test method in the class.
        super.tearDown()
    }
    
    func testExample() {
        // This is an example of a functional test case.
        // Use XCTAssert and related functions to verify your tests produce the correct results.
        /*let coverImage = UIImage(named: "testPlainCoverImage")
        
        // first run
        print("[INFO] Starting first test...")
        let firstMessage = "thisisatest"
        let firstKey = "thisisatest"
        guard let firstRunImage = ChannelSwitchEncryptionEngine.encrypt(message: firstMessage, key: firstKey, image: coverImage!) else {
            XCTAssertTrue(false)
            return
        }
        guard let firstRunMessage = ChannelSwitchEncryptionEngine.decrypt(key: firstKey, image: firstRunImage) else {
            XCTAssertTrue(false)
            return
        }
        XCTAssertTrue(firstMessage == firstRunMessage)
        print("[INFO] Finishing first run.")
        
        // second run
        print("[INFO] Starting second test...")
        let secondMessage = "this is the second test"
        let secondKey = "thisaisatheasecondatest"
        guard let secondRunImage = ChannelSwitchEncryptionEngine.encrypt(message: secondMessage, key: secondKey, image: coverImage!) else {
            XCTAssertTrue(false)
            return
        }
        guard let secondRunMessage = ChannelSwitchEncryptionEngine.decrypt(key: secondKey, image: secondRunImage) else {
            XCTAssertTrue(false)
            return
        }
        XCTAssertTrue(secondMessage == secondRunMessage)
        print("[INFO] Finishing second run.")
        
        // third run
        print("[INFO] Starting third test...")
        let thirdMessage = "thisisthethirdtestwhichhasalongmessage"
        let thirdKey = "butthisistooshort"
        guard let thirdRunImage = ChannelSwitchEncryptionEngine.encrypt(message: thirdMessage, key: thirdKey, image: coverImage!) else {
            XCTAssertTrue(false)
            return
        }
        guard let thirdRunMessage = ChannelSwitchEncryptionEngine.decrypt(key: thirdKey, image: thirdRunImage) else {
            XCTAssertTrue(false)
            return
        }
        XCTAssertTrue(thirdMessage == thirdRunMessage)
        print("[INFO] Finishing third run.")*/
    }
    
    func testPerformanceExample() {
        // This is an example of a performance test case.
        self.measure {
            // Put the code you want to measure the time of here.
        }
    }
}
