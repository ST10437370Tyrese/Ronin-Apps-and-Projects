using System;
using System.Collections.Generic;
using System.Text;

namespace EasyEggs
{
    public class EggBusiness
    {
        // Class properties
        public int EggsBought { get; set; }
        public int EggsBroken { get; set; }
        public double CostPricePerEgg { get; set; }
        public int PacketsSold { get; set; }

        // Constants
        private const int EGGS_PER_PACKET = 6;

        // Constructor
        public EggBusiness(int eggsBought, int eggsBroken, double costPricePerEgg, int packetsSold)
        {
            EggsBought = eggsBought;
            EggsBroken = eggsBroken;
            CostPricePerEgg = costPricePerEgg;
            PacketsSold = packetsSold;
        }

        /// <summary>
        /// Calculates the percentage of eggs that broke during packing
        /// Formula: (Broken Eggs / Total Eggs Bought) * 100
        /// </summary>
        public double CalculateBreakagePercentage()
        {
            if (EggsBought == 0) return 0;
            return (double)EggsBroken / EggsBought * 100;
        }

        /// <summary>
        /// Calculates the number of eggs taken for personal use
        /// Formula: Total eggs bought - broken eggs - (packets sold * eggs per packet)
        /// </summary>
        public int CalculatePersonalUseEggs()
        {
            int eggsInSoldPackets = PacketsSold * EGGS_PER_PACKET;
            int remainingEggs = EggsBought - EggsBroken - eggsInSoldPackets;

            // If remaining eggs are negative, that means we sold more packets than available eggs
            // In that case, return 0 for personal use
            return remainingEggs > 0 ? remainingEggs : 0;
        }

        /// <summary>
        /// Calculates total cost of all eggs
        /// </summary>
        public double CalculateTotalCost()
        {
            return EggsBought * CostPricePerEgg;
        }

        /// <summary>
        /// Calculates total revenue from sold packets
        /// Each packet is sold for 5.00 (you can adjust this price)
        /// </summary>
        public double CalculateTotalRevenue()
        {
            const double pricePerPacket = 5.00;
            return PacketsSold * pricePerPacket;
        }

        /// <summary>
        /// Calculates total profit
        /// </summary>
        public double CalculateProfit()
        {
            return CalculateTotalRevenue() - CalculateTotalCost();
        }

        /// <summary>
        /// Calculates number of complete packets that could be made
        /// </summary>
        public int CalculateTotalPacketsPossible()
        {
            int goodEggs = EggsBought - EggsBroken;
            return goodEggs / EGGS_PER_PACKET;
        }

        /// <summary>
        /// Calculates remaining loose eggs after packing
        /// </summary>
        public int CalculateLooseEggsRemaining()
        {
            int goodEggs = EggsBought - EggsBroken;
            return goodEggs % EGGS_PER_PACKET;
        }
    }
}