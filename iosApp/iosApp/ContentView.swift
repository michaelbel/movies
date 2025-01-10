import UIKit
import SwiftUI
import iosAppCompose

struct ComposeView: UIViewControllerRepresentable {
    func makeUIViewController(context: Context) -> UIViewController {
        MainViewControllerKt.MainViewController()
    }
    func updateUIViewController(_ uiViewController: UIViewController, context: Context) {}
}

struct ContentView: View {
    var body: some View {
        ComposeView()
                .ignoresSafeArea() // Ignore status bar & navigation bar
                .ignoresSafeArea(.keyboard) // Compose has own keyboard handler
    }
}