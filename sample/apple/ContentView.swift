import Darwin
import Foundation
import sample
import SwiftUI

struct ContentView: View {
    init() {
#if targetEnvironment(simulator)
        let report = FileManager.default.urls(for: .cachesDirectory, in: .userDomainMask).first!
            .appendingPathComponent("vulkan-sample.txt")
        FileManager.default.createFile(atPath: report.path, contents: nil)
        freopen(report.path, "w", stdout)
#endif
        runSample()
    }

    private func runSample() {
        let sample = Sample()
        defer { sample.close() }
        sample.run()
    }

    var body: some View {
        VStack {
            Text("Hello, world!")
        }
        .padding()
    }
}

#Preview {
    ContentView()
}
