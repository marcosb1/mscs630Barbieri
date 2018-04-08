//: Playground - noun: a place where people can play

import UIKit

func performRectangleDetection(image: CIImage) -> CIImage? {
    var resultImage: CIImage?
    resultImage = image
    let detector = CIDetector(ofType: CIDetectorTypeRectangle, context: nil, options: [CIDetectorAccuracy: CIDetectorAccuracyHigh, CIDetectorAspectRatio: 1.6, CIDetectorMaxFeatureCount: 10] )!
    
    // Get the detections
    var halfPerimiterValue = 0.0 as Float;
    let features = detector.features(in: image)
    print("feature \(features.count)")
    for feature in features as! [CIRectangleFeature] {
        
        let p1 = feature.topLeft
        let p2 = feature.topRight
        let width = hypotf(Float(p1.x - p2.x), Float(p1.y - p2.y));
        //NSLog(@"xaxis    %@", @(p1.x));
        //NSLog(@"yaxis    %@", @(p1.y));
        let p3 = feature.topLeft
        let p4 = feature.bottomLeft
        let height = hypotf(Float(p3.x - p4.x), Float(p3.y - p4.y));
        let currentHalfPerimiterValue = height+width;
        if (halfPerimiterValue < currentHalfPerimiterValue)
        {
            halfPerimiterValue = currentHalfPerimiterValue
            resultImage = cropBusinessCardForPoints(image: image, topLeft: feature.topLeft, topRight: feature.topRight,
                                                    bottomLeft: feature.bottomLeft, bottomRight: feature.bottomRight)
            print("perimmeter   \(halfPerimiterValue)")
        }
        
    }
    
    return resultImage
}

func cropBusinessCardForPoints(image: CIImage, topLeft: CGPoint, topRight: CGPoint, bottomLeft: CGPoint, bottomRight: CGPoint) -> CIImage {
    
    var businessCard: CIImage
    businessCard = image.applyingFilter(
        "CIPerspectiveTransformWithExtent",
        withInputParameters: [
            "inputExtent": CIVector(cgRect: image.extent),
            "inputTopLeft": CIVector(cgPoint: topLeft),
            "inputTopRight": CIVector(cgPoint: topRight),
            "inputBottomLeft": CIVector(cgPoint: bottomLeft),
            "inputBottomRight": CIVector(cgPoint: bottomRight)])
    businessCard = image.cropping(to: businessCard.extent)
    
    return businessCard
}
